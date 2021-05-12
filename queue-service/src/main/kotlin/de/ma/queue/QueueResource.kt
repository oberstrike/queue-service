package de.ma.queue

import de.ma.util.PagedRequest
import de.ma.util.PagedResponse
import de.ma.util.SortedRequest
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.*

@Path("/queue")
@ApplicationScoped
@Tag(name = "Queue")
class QueueResource {

    @Inject
    lateinit var queueService: QueueService

    @POST
    fun createQueue(queueCreateDTO: QueueCreateDTO) {
        queueService.createQueue(queueCreateDTO)
    }

    @GET
    fun getQueuesByQuery(
        @BeanParam pagedRequest: PagedRequest,
        @BeanParam sortedRequest: SortedRequest
    ): PagedResponse<QueueDTO> {
        return queueService.getQueuesByQuery(pagedRequest, sortedRequest)
    }

    @DELETE
    @Path("/{id}")
    fun deleteQueryById(@PathParam("id") id: Long) {
        queueService.deleteQueueById(id)
    }

    @GET
    @Path("/{id}")
    fun getQueryById(@PathParam("id") id: Long): QueueDTO? {
        return queueService.findById(id)
    }


    @PUT
    fun updateQueue(queueDTO: QueueDTO): QueueDTO? {
        return queueService.update(queueDTO)
    }
}
