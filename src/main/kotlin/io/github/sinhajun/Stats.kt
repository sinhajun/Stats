package io.github.sinhajun

import io.github.sinhajun.changeStats.Change
import io.github.sinhajun.damage.Damage
import io.github.sinhajun.showStats.Show
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

lateinit var configurationFile: File
lateinit var stats: JavaPlugin

class Stats: JavaPlugin() {
    override fun onEnable() {
        configurationFile = File("", "stats.yml")
        stats = this
        val listenerList = arrayOf(LoadConfigFile(), Show(), Change(), Damage())

        listenerList.forEach {listener ->
            server.pluginManager.registerEvents(listener, this)
        }
    }

    override fun onDisable() {
        Bukkit.getOnlinePlayers().forEach {player ->
            val configuration = YamlConfiguration.loadConfiguration(configurationFile)

            configuration[player.name] = arrayListOf(StatsMap.hotMap[player]!!, StatsMap.coldMap[player]!!, StatsMap.thirstyMap[player]!!)

            try {
                configuration.save(configurationFile)
            } catch (e: IOException) { e.printStackTrace() }
        }
    }
}
