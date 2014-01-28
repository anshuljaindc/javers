package org.javers.core.metamodel.property

import java.lang.reflect.Type

/**
 * @author bartosz walacik
 */
class PropertyAssert {
    private Property actual

    private PropertyAssert(Property actual) {
        this.actual = actual
    }

    static PropertyAssert assertThat(Property actual) {
        new PropertyAssert(actual)
    }

    PropertyAssert hasJavaType(Type expected) {
        assert actual.genericType == expected
        this
    }

    PropertyAssert looksLikeId() {
        assert actual.looksLikeId()
        this
    }
}
