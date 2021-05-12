package de.ma.queue

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Queue(
    @ElementCollection
    var players: MutableSet<String> = mutableSetOf(),
    @Enumerated(EnumType.STRING)
    var type: QueueType = QueueType.FOUR_VS_FOUR,
    var isActive: Boolean = false,
    var startDateTime: LocalDateTime = LocalDateTime.now()

) : PanacheEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Queue) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Queue(players=$players, type=$type, isActive=$isActive, startDateTime=$startDateTime)"
    }
}


enum class QueueType(val size : Int) {
    FOUR_VS_FOUR(8),
    ONE_VS_ONE(2)
}

