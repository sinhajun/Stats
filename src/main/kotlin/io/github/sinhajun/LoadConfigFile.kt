package io.github.sinhajun

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.io.IOException

class LoadConfigFile: Listener {

    //TODO config 파일 저장 안되는 버그 고치기
    @EventHandler
    fun load(event: PlayerJoinEvent) {
        val player = event.player
        val configuration = YamlConfiguration.loadConfiguration(configurationFile)

        if (configuration[player.name] == null) configuration[player.name] = arrayListOf(0, 0, 0)

        StatsMap.hotMap[player] = configuration.getList(player.name)!![0] as Int
        StatsMap.coldMap[player] = configuration.getList(player.name)!![1] as Int
        StatsMap.thirstyMap[player] = configuration.getList(player.name)!![2] as Int
    }
    @EventHandler
    fun save(event: PlayerQuitEvent) {
        val player = event.player
        val configuration = YamlConfiguration.loadConfiguration(configurationFile)

        configuration[player.name] = arrayListOf(StatsMap.hotMap[player]!!, StatsMap.coldMap[player]!!, StatsMap.thirstyMap[player]!!)

        try {
            configuration.save(configurationFile)
        } catch (e: IOException) { e.printStackTrace() }
    }
}
