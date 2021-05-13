package de.ma.lobby


import de.ma.util.PagedRequest
import de.ma.util.PagedResponse
import de.ma.util.SortedRequest
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.*


@Path("/lobby")
@ApplicationScoped
@Tag(name = "Lobby")
class LobbyResource {
    @Inject
    lateinit var lobbyService: LobbyService

    @POST
    fun createLobby(lobbyCreateDTO: LobbyCreateDTO) {
        lobbyService.createLobby(lobbyCreateDTO)
    }

    @GET
    fun getLobbysByQuery(
        @BeanParam pagedRequest: PagedRequest,
        @BeanParam sortedRequest: SortedRequest
    ): PagedResponse<LobbyDTO> {
        return lobbyService.getLobbysByQuery(pagedRequest, sortedRequest)
    }

    @DELETE
    @Path("/{id}")
    fun deleteLobbyById(@PathParam("id") id: Long) {
        lobbyService.deleteLobbyById(id)
    }

    @GET
    @Path("/{id}")
    fun getLobbyById(@PathParam("id") id: Long): LobbyDTO? {
        return lobbyService.findById(id)
    }


    @PUT
    fun updateLobby( lobbyDTO: LobbyDTO): LobbyDTO? {
        return lobbyService.update(lobbyDTO)
    }
}
