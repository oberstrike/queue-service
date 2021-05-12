package de.ma.unit.util

import io.quarkus.test.h2.H2DatabaseTestResource
import io.quarkus.test.junit.QuarkusTestProfile


class UnitTestProfile : QuarkusTestProfile {

    override fun testResources(): MutableList<QuarkusTestProfile.TestResourceEntry> {
        return mutableListOf(
            QuarkusTestProfile.TestResourceEntry(
                H2DatabaseTestResource::class.java
            )
        )
    }

    override fun getConfigProfile(): String {
        return "unit"
    }

}
