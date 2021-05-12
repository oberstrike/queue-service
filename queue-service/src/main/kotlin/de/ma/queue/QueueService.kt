package de.ma.queue

import de.ma.util.PagedRequest
import de.ma.util.PagedResponse
import de.ma.util.SortedRequest

interface QueueService {
    fun createQueue(queueCreateDTO: QueueCreateDTO): QueueDTO
    fun findById(queueId: Long): QueueDTO?
    fun getQueuesByQuery(pagedRequest: PagedRequest, sortedRequest: SortedRequest): PagedResponse<QueueDTO>
    fun deleteQueueById(id: Long)
    fun update(queueDTO: QueueDTO): QueueDTO
}
