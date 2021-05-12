package de.ma.config

import de.ma.queue.QueueMapper
import de.ma.queue.QueueRepository
import de.ma.queue.QueueRepositoryProxy
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

@ApplicationScoped
class Config {

    @Produces
    fun queueRepositoryProxy(queueConverter: QueueMapper,  queueRepository: QueueRepository) =
        QueueRepositoryProxy(queueConverter, queueRepository)

}
