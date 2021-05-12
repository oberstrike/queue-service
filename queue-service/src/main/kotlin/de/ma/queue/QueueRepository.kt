package de.ma.queue

import com.maju.annotations.RepositoryProxy
import de.ma.util.PagedRequest
import de.ma.util.SortedRequest
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@RepositoryProxy(converters = [QueueMapper::class])
class QueueRepository : PanacheRepository<Queue> {

    fun save(queueToUpdate: Queue): Queue {
        val oldQueue = queueToUpdate.id?.let { findById(it) }

        if (oldQueue != null) {
            return update(oldQueue, queueToUpdate)
        }

        persist(queueToUpdate)
        flush()
        return queueToUpdate

    }

    private fun update(
        oldQueue: Queue,
        queueToUpdate: Queue
    ): Queue {
        oldQueue.type = queueToUpdate.type
        queueToUpdate.players.forEach { player ->
            if (!oldQueue.players.contains(player)) {
                oldQueue.players.add(player)
                oldQueue.players.add(player)
            }
        }

        oldQueue.players.forEach { player ->
            if (!queueToUpdate.players.contains(player)) {
                oldQueue.players.removeIf{ it == player}
            }
        }
        persist(oldQueue)
        flush()
        return oldQueue
    }

    fun findByQuery(pagedRequest: PagedRequest, sortedRequest: SortedRequest): PanacheQuery<Queue> {
        val sorting = Sort.by(sortedRequest.sort, sortedRequest.dir.content)
        val paging = Page.of(pagedRequest.page, pagedRequest.pageSize)

        return findAll(sorting).page(paging)
    }

}
