package io.github.sinhajun

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class LoadConfigFile : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val config = Stats().config
        if (config.get(event.player.name) == null) config.set(event.player.name, arrayListOf(0, 0, 0))

        StatsMap.hotMap[event.player] = config.getList(event.player.name)!![0] as Int
        StatsMap.coldMap[event.player] = config.getList(event.player.name)!![1] as Int
        StatsMap.thirstyMap[event.player] = config.getList(event.player.name)!![2] as Int
    }
}