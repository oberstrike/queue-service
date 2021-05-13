package de.ma.unit.domain.lobby

import de.ma.lobby.LobbyCreateDTO
import de.ma.unit.util.AbstractUnitTest
import de.ma.unit.util.UnitTestProfile
import de.ma.util.PagedRequest
import de.ma.util.SortedRequest
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.TestProfile
import org.junit.jupiter.api.Test

@QuarkusTest
@TestProfile(UnitTestProfile::class)
class LobbyServiceTest : AbstractUnitTest() {

    private val playerDTO = "PlayerDTO"

    @Test
    fun `Test searching by query`() = withLobby {
        val result = lobbyService.getLobbysByQuery(
            PagedRequest(),
            SortedRequest()
        )

        val content = result.content
        content.size shouldBe 1
    }

    @Test
    fun `Add a player to lobby and check if he is added`() = withLobby { lobbyDTO ->
            val updatedLobbyDTO = lobbyService.update(lobbyDTO.apply { players.add(playerDTO) })
            updatedLobbyDTO shouldNot beNull()
            val result = lobbyService.findById(lobbyDTO.id!!)
            result shouldNot beNull()
            result!!.players.size shouldBe 1

    }

    @Test
    fun `Create a lobby and delete it`() {
        val lobbyCreateDTO = LobbyCreateDTO()
        val createdLobby = lobbyService.createLobby(lobbyCreateDTO)
        createdLobby shouldNot beNull()
        val lobbyId = createdLobby.id
        lobbyId shouldNot beNull()
        lobbyService.deleteLobbyById(lobbyId!!)
        val resultLobby = lobbyService.findById(lobbyId)
        resultLobby should beNull()
    }

    @Test
    fun `Add a player to Lobby, remove him and check if he is gone`() {
        var lobbyID: Long? = 0

        withLobby(shouldBeRemoved = true) { lobbyDTO ->
            lobbyID = lobbyDTO.id
                val newPlayerDTO =
                    lobbyService.update(lobbyDTO.apply { players.add(playerDTO) })!!.players.first()
                newPlayerDTO shouldNot beNull()
                val sample = lobbyService.findById(lobbyDTO.id!!)
                sample shouldNot beNull()
                sample!!.players.size shouldBe 1

                val newLobbyDTO = lobbyDTO.apply {
                    players = mutableListOf()
                }

                lobbyService.update(newLobbyDTO)
            }

            val result = lobbyService.findById(lobbyID!!)

            result shouldNot beNull()
            result!!.players.size shouldBe 0
        }




}
