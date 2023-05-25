package io.github.sinhajun.show

import io.github.sinhajun.StatsMap
import io.github.sinhajun.stats
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

class Show: Listener {

    @EventHandler
    fun show(event: PlayerJoinEvent) {
        val player = event.player
        object : BukkitRunnable() {
            override fun run() {
                player.sendActionBar(Component.text("§c§l더위 : §4§l${StatsMap.hotMap[player]} §b§l추위 : §3§l${StatsMap.coldMap[player]} §9§l목마름 : §1§l${StatsMap.thirstyMap[player]}"))
            }
        }.runTaskTimer(stats, 0L, 0L)
    }
}