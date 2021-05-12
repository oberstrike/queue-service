package de.ma.rest.util


import de.ma.rest.util.keycloak.KeycloakService
import de.ma.unit.util.AbstractUnitTest
import io.kotest.matchers.shouldNotBe
import javax.inject.Inject

abstract class AbstractRestTest : AbstractUnitTest() {

    @Inject
    lateinit var keycloakAdminService: KeycloakAdminService

    @Inject
    lateinit var keycloakService: KeycloakService


    companion object {
        const val password: String = "random"
    }


    protected fun <T> withLoggedInPlayer(username: String, password: String, block: (String) -> T): T {
        val keycloak = keycloakService.getKeycloak(username, password)
        val result = "Bearer ${keycloak.tokenManager().accessToken.token}"

        result.length shouldNotBe 0

        return block.invoke(result)
    }


}
