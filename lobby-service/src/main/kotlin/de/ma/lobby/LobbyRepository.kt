package de.ma.lobby

import com.maju.annotations.RepositoryProxy
import de.ma.util.PagedRequest
import de.ma.util.SortedRequest
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@RepositoryProxy(
    converters = [LobbyMapper::class]
)
class LobbyRepository : PanacheRepository<Lobby> {

    fun save(lobbyToUpdate: Lobby): Lobby {
        val oldLobby = lobbyToUpdate.id?.let { findById(it) }

        if (oldLobby != null) {
            oldLobby.type = lobbyToUpdate.type
            for (player in lobbyToUpdate.players) {
                if(!oldLobby.players.contains(player)){
                    oldLobby.players.add(player)
                }
            }

            for (player in oldLobby.players) {
                if (!lobbyToUpdate.players.contains(player)) {
                    oldLobby.players.remove(player)
                }
            }


            persist(oldLobby)
            flush()
            return oldLobby
        }

        persist(lobbyToUpdate)
        flush()
        return lobbyToUpdate

    }

    fun findByQuery(pagedRequest: PagedRequest, sortedRequest: SortedRequest): PanacheQuery<Lobby> {
        val sorting = Sort.by(sortedRequest.sort, sortedRequest.dir.content)
        val paging = Page.of(pagedRequest.page, pagedRequest.pageSize)

        return findAll(sorting).page(paging)
    }

}
