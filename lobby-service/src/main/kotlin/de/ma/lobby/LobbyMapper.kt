package de.ma.lobby

import com.maju.annotations.IConverter
import org.mapstruct.*

@Mapper(
    componentModel = "cdi",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)

interface LobbyMapper : IConverter<Lobby, LobbyDTO> {
    @Mapping(target = "players", ignore = true)
    override fun convertDTOToModel(dto:LobbyDTO): Lobby

}
