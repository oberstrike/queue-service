package de.ma.queue

data class QueueDTO(
    var id: Long? = null,
    var type: QueueType = QueueType.FOUR_VS_FOUR,
    var players: MutableList<String> = mutableListOf()
)


data class QueueCreateDTO(
    var type: QueueType = QueueType.FOUR_VS_FOUR
)
