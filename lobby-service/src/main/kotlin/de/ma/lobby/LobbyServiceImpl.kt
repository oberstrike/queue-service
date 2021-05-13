package de.ma.lobby

import de.ma.util.PagedRequest
import de.ma.util.PagedResponse
import de.ma.util.SortedRequest
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException

@ApplicationScoped
class LobbyServiceImpl(
    private val lobbyRepositoryProxy: LobbyRepositoryProxy
) : LobbyService {

    @Transactional
    override fun createLobby(lobbyCreateDTO: LobbyCreateDTO): LobbyDTO {
        return lobbyRepositoryProxy.save(LobbyDTO(type = lobbyCreateDTO.type))
    }

    @Transactional
    override fun findById(lobbyId: Long): LobbyDTO? {
        return lobbyRepositoryProxy.findById(lobbyId)
    }

    @Transactional
    override fun getLobbysByQuery(
        pagedRequest: PagedRequest,
        sortedRequest: SortedRequest
    ): PagedResponse<LobbyDTO> {
        val lobbyDTOs: PanacheQuery<Lobby> =
            lobbyRepositoryProxy.repository.findByQuery(pagedRequest, sortedRequest)
        return PagedResponse(
            content = lobbyRepositoryProxy.lobbyMapper.convertModelsToDTOs(lobbyDTOs.list()),
            page = pagedRequest.page,
            pageCount = lobbyDTOs.pageCount()
        )
    }

    @Transactional
    override fun update(lobbyDTO: LobbyDTO): LobbyDTO {
        val lobbyId = lobbyDTO.id

        if (lobbyId != null) {
            findById(lobbyId)
                ?: throw NotFoundException("There was no question with the id $lobbyId found")
            return lobbyRepositoryProxy.save(lobbyDTO)
        } else {
            throw BadRequestException("The question needs an id")
        }
    }

    @Transactional
    override fun deleteLobbyById(id: Long) {
        lobbyRepositoryProxy.deleteById(id)

    }
}
