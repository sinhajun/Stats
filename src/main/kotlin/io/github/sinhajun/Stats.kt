package io.github.sinhajun

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import io.github.sinhajun.changeStats.Change
import io.github.sinhajun.showStats.Show
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

lateinit var configuration: FileConfiguration
lateinit var stats: JavaPlugin
lateinit var protocolManager: ProtocolManager

class Stats: JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()
        reloadConfig()
        configuration = config
        stats = this
        protocolManager = ProtocolLibrary.getProtocolManager()
        val listenerList = arrayOf(LoadConfigFile(), Show(), Change(), Change().Drink())

        listenerList.forEach {listener ->
            server.pluginManager.registerEvents(listener, this)
        }
    }

    override fun onDisable() {
        saveConfig()
    }
}
