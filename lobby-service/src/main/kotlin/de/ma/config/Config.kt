package de.ma.config

import de.ma.lobby.LobbyMapper
import de.ma.lobby.LobbyRepository
import de.ma.lobby.LobbyRepositoryProxy
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

@ApplicationScoped
class Config {


    @Produces
    fun lobbyRepositoryProxy(lobbyConverter: LobbyMapper, lobbyRepository: LobbyRepository) =
        LobbyRepositoryProxy(lobbyConverter, lobbyRepository)

}
