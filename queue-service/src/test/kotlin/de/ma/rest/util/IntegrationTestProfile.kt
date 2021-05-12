package de.ma.rest.util

import de.ma.rest.util.keycloak.KeycloakContainerCreator
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.quarkus.test.junit.QuarkusTestProfile
import org.testcontainers.containers.GenericContainer
import io.quarkus.test.h2.H2DatabaseTestResource;

class IntegrationTestProfile: QuarkusTestProfile {

    override fun testResources(): MutableList<QuarkusTestProfile.TestResourceEntry> {
        return mutableListOf(
            QuarkusTestProfile.TestResourceEntry(
                IntegrationDockerTestResource::class.java
            ),
            QuarkusTestProfile.TestResourceEntry(
                H2DatabaseTestResource::class.java
            )
        )
    }

}

class IntegrationDockerTestResource: QuarkusTestResourceLifecycleManager{

    private val listOfContainerCreator = listOf(KeycloakContainerCreator())

    private val listOfContainer = mutableListOf<GenericContainer<*>>()

    override fun start(): MutableMap<String, String> {
        val resultConfig = mutableMapOf<String, String>()

        for (creator in listOfContainerCreator) {
            val config = creator.getConfig()
            val container = creator.getContainer()

            println("Starting ${container.dockerImageName}")
            if (!container.isRunning) container.start()

            with(listOfContainer) {
                if (!contains(container)) add(container)
            }

            println("Started ${container.dockerImageName}")
            resultConfig.putAll(config)
        }


        println("Starting with config: $resultConfig")
        return resultConfig
    }

    override fun stop() {
        listOfContainer.forEach { container ->
            println("Stopping ${container.dockerImageName}")
            if (container.isRunning) container.stop()
            println("Stopped ${container.dockerImageName}")
        }
    }
}
