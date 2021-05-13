package de.ma.lobby

import de.ma.util.PagedRequest
import de.ma.util.PagedResponse
import de.ma.util.SortedRequest

interface LobbyService {
    fun createLobby(lobbyCreateDTO: LobbyCreateDTO): LobbyDTO
    fun findById(lobbyId: Long): LobbyDTO?
    fun getLobbysByQuery(pagedRequest: PagedRequest, sortedRequest: SortedRequest): PagedResponse<LobbyDTO>
    fun deleteLobbyById(id: Long)
    fun update(lobbyDTO: LobbyDTO): LobbyDTO
}
