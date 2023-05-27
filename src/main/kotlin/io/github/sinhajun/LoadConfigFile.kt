package io.github.sinhajun

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class LoadConfigFile: Listener {

    //TODO config 파일 저장 안되는 버그 고치기
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (configuration[player.name] == null) configuration[player.name] = arrayListOf(0, 0, 0)

        StatsMap.hotMap[player] = configuration.getList(player.name)!![0] as Int
        StatsMap.coldMap[player] = configuration.getList(player.name)!![1] as Int
        StatsMap.thirstyMap[player] = configuration.getList(player.name)!![2] as Int
    }
}
