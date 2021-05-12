package de.ma.queue

import de.ma.util.PagedRequest
import de.ma.util.PagedResponse
import de.ma.util.SortedRequest
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException

@ApplicationScoped
class QueueServiceImpl(
    private val queueRepositoryProxy: QueueRepositoryProxy
) : QueueService {

    @Transactional
    override fun createQueue(queueCreateDTO: QueueCreateDTO): QueueDTO {
        return queueRepositoryProxy.save(QueueDTO(type = queueCreateDTO.type))
    }

    @Transactional
    override fun findById(queueId: Long): QueueDTO? {
        return queueRepositoryProxy.findById(queueId)
    }

    @Transactional
    override fun getQueuesByQuery(
        pagedRequest: PagedRequest,
        sortedRequest: SortedRequest
    ): PagedResponse<QueueDTO> {
        val queueDTOs: PanacheQuery<Queue> =
            queueRepositoryProxy.repository.findByQuery(pagedRequest, sortedRequest)
        return PagedResponse(
            content = queueRepositoryProxy.queueMapper.convertModelsToDTOs(queueDTOs.list()),
            page = pagedRequest.page,
            pageCount = queueDTOs.pageCount()
        )
    }

    @Transactional
    override fun update(queueDTO: QueueDTO): QueueDTO {
        val queueId = queueDTO.id

        if (queueId != null) {
            findById(queueId)
                ?: throw NotFoundException("There was no question with the id $queueId found")
            return queueRepositoryProxy.save(queueDTO)
        } else {
            throw BadRequestException("The question needs an id")
        }
    }

    @Transactional
    override fun deleteQueueById(id: Long) {
        queueRepositoryProxy.deleteById(id)

    }
}
