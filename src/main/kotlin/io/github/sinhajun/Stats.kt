package io.github.sinhajun

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class Stats : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(LoadConfigFile(), this)
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach {
                    it?.sendMessage(Component.text("${StatsMap.hotMap}"))
                    it?.sendMessage(Component.text("${StatsMap.coldMap}"))
                    it?.sendMessage(Component.text("${StatsMap.thirstyMap}"))
                }
            }
        }.runTaskTimer(this, 0L, 40L)
    }

    override fun onDisable() {
        saveConfig()
    }
}