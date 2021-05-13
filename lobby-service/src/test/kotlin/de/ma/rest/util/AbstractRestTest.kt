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

/*
    override fun createPlayers(count: Int): List<PlayerDTO> {
        return (0 until count)
            .map { UserDTO(username = "player#$it") }
            .map { keycloakAdminService.register(it.username, password, "${it.username}@gmx.de"); it }
            .map { PlayerCreateDTO(keycloakAdminService.getUserIdByUsername(it.username)!!.id) }
            .map { playerService.create(it) }
    }

    override fun deletePlayer(playerDTO: PlayerDTO) {
        keycloakAdminService.deleteById(playerDTO.userId)
        super.deletePlayer(playerDTO)
    }
    */

}
