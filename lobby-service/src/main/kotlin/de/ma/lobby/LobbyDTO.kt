package de.ma.lobby


data class LobbyDTO(
    var id: Long? = null,
    var type: LobbyType = LobbyType.FOUR_VS_FOUR,
    var players: MutableList<String> = mutableListOf()
)


data class LobbyCreateDTO(
    var type: LobbyType = LobbyType.FOUR_VS_FOUR
)
