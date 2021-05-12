package de.ma.unit.util

import de.ma.queue.QueueCreateDTO
import de.ma.queue.QueueDTO
import de.ma.queue.QueueService
import de.ma.queue.QueueType
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import javax.inject.Inject
import javax.transaction.Transactional

open class AbstractUnitTest {

    @Inject
    protected lateinit var queueService: QueueService


    @Transactional
    protected fun <T> withQueue(
        queueType: QueueType = QueueType.ONE_VS_ONE,
        players: List<String> = emptyList(),
        shouldBeRemoved: Boolean = true,
        block: (QueueDTO) -> T
    ) {
        val createQueueDTO = QueueCreateDTO(queueType)
        val createdQueue = queueService.createQueue(createQueueDTO)
        createdQueue shouldNot beNull()
        createdQueue.players.addAll(players)
        block.invoke(queueService.update(createdQueue))
        if (shouldBeRemoved) {
            createdQueue.players.clear()
            queueService.update(createdQueue)
            queueService.deleteQueueById(createdQueue.id!!)
            val comparison = queueService.findById(createdQueue.id!!)
            comparison should beNull()
        }


    }
}
