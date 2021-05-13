package de.ma.unit.util

import de.ma.lobby.LobbyCreateDTO
import de.ma.lobby.LobbyDTO
import de.ma.lobby.LobbyService
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import javax.inject.Inject
import javax.transaction.Transactional

open class AbstractUnitTest {

    data class PlayerCreateDTO(val userId: String)

    @Inject
    protected lateinit var lobbyService: LobbyService

    protected fun <T> withLobby(
        players: List<String> = emptyList(),
        shouldBeRemoved: Boolean = true,
        block: (LobbyDTO) -> T
    ) {
        val lobbyCreateDTO = LobbyCreateDTO()
        var createdLobby = lobbyService.createLobby(lobbyCreateDTO)
        createdLobby shouldNot beNull()

        if (players.isNotEmpty()) {
            createdLobby.players.addAll(players)
            createdLobby = lobbyService.update(createdLobby)
        }

        block.invoke(createdLobby)

        if (shouldBeRemoved) {
            lobbyService.deleteLobbyById(createdLobby.id!!)
            val comparison = lobbyService.findById(createdLobby.id!!)
            comparison should beNull()
        }

    }

}
