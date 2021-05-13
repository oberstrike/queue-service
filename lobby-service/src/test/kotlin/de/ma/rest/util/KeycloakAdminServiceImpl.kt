package de.ma.rest.util

import de.ma.rest.util.keycloak.KeycloakContainerCreator
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import java.lang.Exception
import java.nio.file.attribute.UserPrincipalNotFoundException
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class KeycloakAdminServiceImpl : KeycloakAdminService {

    private var keycloak = KeycloakBuilder.builder()
        .serverUrl("http://${KeycloakContainerCreator.AUTH_SERVER_HOST}:${KeycloakContainerCreator.PORT}/auth")
        .realm("master")
        .clientId("admin-cli")
        .clientSecret(KeycloakContainerCreator.SECRET)
        .username(KeycloakContainerCreator.ADMIN_USERNAME)
        .password(KeycloakContainerCreator.ADMIN_PASSWORD)
        .build()

    override fun hasRoleByUserId(role: String, id: String): Boolean {
        val userResource = keycloak.realm(KeycloakContainerCreator.REALM_NAME)
            .users()
        return userResource.get(id).roles().realmLevel().listAll().map { it.name }.contains(role)
    }

    override fun addRoleToUserById(role: String, id: String) {
        val frontendUserRole = keycloak.realm(KeycloakContainerCreator.REALM_NAME)
            .roles()
            .list()
            .stream()
            .filter { roleRepresentation: RoleRepresentation -> role == roleRepresentation.name }
            .findFirst().get()


        keycloak.realm(KeycloakContainerCreator.REALM_NAME)
            .users()
            .get(id)
            .roles()
            .realmLevel()
            .add(listOf(frontendUserRole))

    }


    override fun deleteByName(username: String) {
        val realmResource = keycloak.realm(KeycloakContainerCreator.REALM_NAME)
        val usersResource = realmResource.users()
        val user = usersResource.list().firstOrNull { it.username == username }
            ?: throw UserPrincipalNotFoundException(
                username
            )

        val id = user.id
        usersResource.delete(id)
    }

    override fun deleteById(id: String) {
        val realmResource = keycloak.realm(KeycloakContainerCreator.REALM_NAME)
        val usersResource = realmResource.users()
        usersResource.delete(id)
    }


    override fun register(username: String, password: String, email: String) {
        val realmResource = keycloak.realm(KeycloakContainerCreator.REALM_NAME)
        val usersResource = realmResource.users()

        val userRepresentation = UserRepresentation()
        userRepresentation.username = username
        userRepresentation.email = email
        userRepresentation.isEmailVerified = false
        userRepresentation.isEnabled = true

        val credentialRepresentation = CredentialRepresentation()
        credentialRepresentation.isTemporary = false
        credentialRepresentation.type = CredentialRepresentation.PASSWORD
        credentialRepresentation.value = password
        userRepresentation.credentials = listOf(credentialRepresentation)

        val response = usersResource.create(userRepresentation)
         when (response.status) {
            201 -> {
                val userId = response.location.path.split("/").last()
                addRoleToUserById("user", userId)
            }
            else -> {
                throw Exception("Oops there was an error in the registration")
            }
        }
    }

    override fun getUserIdByUsername(username: String): UserDTO? {
        val realmResource = keycloak.realm("quarkus")
        val usersResource = realmResource.users()

        return usersResource.search(username).map { UserDTO(it.id, it.username, it.email) }.firstOrNull()
    }

    override fun resetPassword(email: String, newPassword: String) {
        val userId = keycloak.realm("quarkus")
            .users()
            .search(email, null, null)
            .stream()
            .filter { email == it.email }
            .map(UserRepresentation::getId)
            .findFirst()
            .orElse(null)
            ?: throw UserPrincipalNotFoundException("There is no user with the email $email")

        val credentialRepresentation = CredentialRepresentation()
        credentialRepresentation.isTemporary = false
        credentialRepresentation.type = CredentialRepresentation.PASSWORD
        credentialRepresentation.value = newPassword
        keycloak.realm("quarkus")
            .users()
            .get(userId)
            .resetPassword(credentialRepresentation)
    }

}
