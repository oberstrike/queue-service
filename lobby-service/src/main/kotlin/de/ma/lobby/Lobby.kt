package de.ma.lobby

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Lobby(
    @ElementCollection
    var players: MutableSet<String> = mutableSetOf(),
    @Enumerated(EnumType.STRING)
    var type: LobbyType = LobbyType.FOUR_VS_FOUR,
    var isActive: Boolean = false,
    var startDateTime: LocalDateTime = LocalDateTime.now()
) : PanacheEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Lobby) return false
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


enum class LobbyType(val size : Int) {
    FOUR_VS_FOUR(8),
    ONE_VS_ONE(2);

    companion object{
        fun bySize(size: Int) = values().find { it.size == size }
    }
}
