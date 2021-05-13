package de.ma.rest.util.keycloak

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import javax.enterprise.context.ApplicationScoped
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

import javax.ws.rs.client.ClientRequestContext

import javax.ws.rs.client.ClientRequestFilter


@ApplicationScoped
class KeycloakService {

    fun getKeycloak(username: String = "alice", password: String = "alice"): Keycloak = KeycloakBuilder.builder() //
        .serverUrl("http://${KeycloakContainerCreator.AUTH_SERVER_HOST}:${KeycloakContainerCreator.PORT}/auth") //
        .realm(KeycloakContainerCreator.REALM_NAME) //
        .username(username) //
        .password( password) //
        .clientId(KeycloakContainerCreator.CLIENT_ID) //
        .clientSecret(KeycloakContainerCreator.SECRET)
        .grantType(OAuth2Constants.PASSWORD) //
        .resteasyClient(ResteasyClientBuilderImpl()
            .register(LoggingFilter())
            .connectionPoolSize(10).build()) //
        .build()
}


class LoggingFilter : ClientRequestFilter {
    @Throws(IOException::class)
    override fun filter(requestContext: ClientRequestContext) {
        LOG.log(Level.INFO, "${requestContext.entity}  + ${requestContext.headers} ")
    }

    companion object {
        private val LOG: Logger = Logger.getLogger(LoggingFilter::class.java.name)
    }
}
