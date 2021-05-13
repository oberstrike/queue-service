package de.ma.rest.util.keycloak

import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import dasniko.testcontainers.keycloak.KeycloakContainer
import de.ma.rest.util.IContainerCreator
import org.testcontainers.containers.GenericContainer

class KeycloakContainerCreator : IContainerCreator<KeycloakContainer> {
    companion object {
        const val AUTH_SERVER_HOST = "localhost"
        const val ADMIN_USERNAME = "admin"
        const val ADMIN_PASSWORD = "admin"
        const val REALM_IMPORT_FILE = "/imports/realm-export.json"
        const val PORT = 8181
        const val IMAGE_NAME = "jboss/keycloak:12.0.1"
        const val REALM_NAME = "quarkus"
        const val CLIENT_ID = "backend-service"
        const val SECRET = "secret"
    }

    override fun getConfig(): MutableMap<String, String> {
        return mutableMapOf(
            "quarkus.oidc.auth-server-url" to "http://$AUTH_SERVER_HOST:$PORT/auth/realms/$REALM_NAME",
            "service.admin.serverUrl" to "http://$AUTH_SERVER_HOST:$PORT/auth",
            "service.admin.loginUrl" to "http://$AUTH_SERVER_HOST:$PORT/auth/realms/$REALM_NAME/account",
            "quarkus.oidc.client-id" to CLIENT_ID,
            "quarkus.oidc.credentials.secret" to SECRET

        )
    }

    override fun getContainer(): GenericContainer<KeycloakContainer> {
        return KeycloakContainer()
            .withAdminUsername(ADMIN_USERNAME)
            .withAdminPassword(ADMIN_PASSWORD)
            .withRealmImportFile(REALM_IMPORT_FILE)
            .withExposedPorts(PORT)
            .withCreateContainerCmdModifier { cmd ->
                cmd.withHostConfig(
                    HostConfig.newHostConfig().withPortBindings(PortBinding.parse("$PORT:8080"))
                )
            }

    }
}
