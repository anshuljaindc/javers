package org.javers.repository.api;

import java.util.Optional;
import org.javers.core.commit.CommitId;
import java.time.LocalDateTime;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Container for additional query parameters
 * used for filtering Snapshots to be fetched from database.
 *
 * @see QueryParamsBuilder
 * @author michal wesolowski
 */
public class QueryParams {
    private final int limit;
    private final int skip;
    private final Optional<LocalDateTime> from;
    private final Optional<LocalDateTime> to;
    private final Set<CommitId> commitIds;
    private final Optional<Long> version;
    private final Optional<String> author;
    private final Optional<Map<String, String>> commitProperties;
    private final boolean aggregate;
    private final boolean newObjectChanges;
    private final Optional<String> changedProperty;

    QueryParams(int limit, int skip, LocalDateTime from, LocalDateTime to, Set<CommitId> commitIds, Long version, String author, Map<String, String> commitProperties, boolean aggregate, boolean newObjectChanges, String changedProperty) {
        this.limit = limit;
        this.skip = skip;
        this.from = Optional.ofNullable(from);
        this.to = Optional.ofNullable(to);
        this.commitIds = commitIds;
        this.version = Optional.ofNullable(version);
        this.author = Optional.ofNullable(author);
        this.commitProperties = Optional.ofNullable(commitProperties);
        this.aggregate = aggregate;
        this.newObjectChanges = newObjectChanges;
        this.changedProperty = Optional.ofNullable(changedProperty);
    }

    public int limit() {
        return limit;
    }

    /**
     * skips a given number of latest snapshots
     */
    public int skip() {
        return skip;
    }

    public boolean hasDates() {
        return from.isPresent() || to.isPresent();
    }

    public boolean isDateInRange(LocalDateTime date) {
        if (from.isPresent() && from.get().isAfter(date)){
            return false;
        }
        if (to.isPresent() && to.get().isBefore(date)){
            return false;
        }

        return true;
    }

    /**
     * selects Snapshots created after this date (or exactly at this date)
     */
    public Optional<LocalDateTime> from() {
        return from;
    }

    /**
     * only Snapshots created before this date (or exactly at this date)
     */
    public Optional<LocalDateTime> to() {
        return to;
    }

    /**
     * filters results to Snapshots with a given commitId
     */
    public Set<CommitId> commitIds() {
        return Collections.unmodifiableSet(commitIds);
    }

    /**
     * filters results to Snapshots with all given commit properties
     */
    public Map<String, String> commitProperties() {
        return commitProperties.isPresent() ?
            commitProperties.get() : Collections.<String, String>emptyMap();
    }

    /**
     * filters results to Snapshots with a given property on changed properties list
     */
    public Optional<String> changedProperty(){
        return changedProperty;
    }

    /**
     * filters results to Snapshots with a given version
     */
    public Optional<Long> version() {
        return version;
    }

    /**
     * filters results to Snapshots committed by a given author
     */
    public Optional<String> author() {
        return author;
    }

    /**
     * When enabled, selects all ValueObjects owned by selected Entities.
     */
    public boolean isAggregate() {
        return aggregate;
    }

    public boolean newObjectChanges() {
        return newObjectChanges;
    }

    @Override
    public String toString() {
        return "QueryParams{" +
                "limit=" + limit +
                ", skip=" + skip +
                ", from=" + from +
                ", to=" + to +
                ", commitIds=" + commitIds +
                ", commitProperties=" + commitProperties +
                ", version=" + version +
                "}";
    }
}
