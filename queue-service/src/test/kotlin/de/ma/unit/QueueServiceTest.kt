package de.ma.unit

import de.ma.queue.QueueCreateDTO
import de.ma.unit.util.AbstractUnitTest
import de.ma.unit.util.UnitTestProfile
import de.ma.util.PagedRequest
import de.ma.util.SortedRequest
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.TestProfile
import org.junit.jupiter.api.Test

@QuarkusTest
@TestProfile(UnitTestProfile::class)
class QueueServiceTest : AbstractUnitTest() {

    //TODO
    private val playerDTO = "Hallo"

    @Test
    fun `Test searching by query`() = withQueue {
        val result = queueService.getQueuesByQuery(
            PagedRequest(),
            SortedRequest()
        )

        val content = result.content
        content.size shouldBe 1
    }

    @Test
    fun `Add a player to queue and check if he is added`() = withQueue { queueDTO ->
        val updatedQueueDTO = queueService.update(queueDTO.apply { players.add(playerDTO) })
        updatedQueueDTO shouldNot beNull()
        val result = queueService.findById(queueDTO.id!!)
        result shouldNot beNull()
        result!!.players.size shouldBe 1
    }

    @Test
    fun `Create a queue and delete it`() {
        val queueCreateDTO = QueueCreateDTO()
        val createdQueue = queueService.createQueue(queueCreateDTO)
        createdQueue shouldNot beNull()
        val queueId = createdQueue.id
        queueId shouldNot beNull()
        queueService.deleteQueueById(queueId!!)
        val resultQueue = queueService.findById(queueId)
        resultQueue should beNull()
    }

    @Test
    fun `Add a player to queue, remove him and check if he is gone`() {
        var queueId: Long? = 0

        withQueue(shouldBeRemoved = true) { queueDTO ->
            queueId = queueDTO.id
            val newPlayerDTO =
                queueService.update(queueDTO.apply { players.add(playerDTO) }).players.first()
            newPlayerDTO shouldNot beNull()
            val sample = queueService.findById(queueDTO.id!!)
            sample shouldNot beNull()
            sample!!.players.size shouldBe 1

            val newQueueDTO = queueDTO.apply {
                players = mutableListOf()
            }

            queueService.update(newQueueDTO)


            val result = queueService.findById(queueId!!)

            result shouldNot beNull()
            result!!.players.size shouldBe 0
        }


    }

}
