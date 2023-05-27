package io.github.sinhajun.damage

import io.github.sinhajun.StatsMap
import io.github.sinhajun.stats
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

class Damage: Listener {
    private val maxStat = 50
    private val damage = 4.0

    @EventHandler
    fun onDamage(event: PlayerJoinEvent) {
        val player = event.player
        when (maxStat) {
            StatsMap.hotMap[player]!!, StatsMap.coldMap[player]!!, StatsMap.thirstyMap[player]!! -> {
                object : BukkitRunnable() {
                    override fun run() {
                        player.damage(damage)
                    }
                }.runTaskTimer(stats, 0L, 20L)
            }
        }

    }

    @EventHandler
    fun onDead(event: PlayerDeathEvent) {
        val player = event.player

        StatsMap.hotMap[player] = 0
        StatsMap.coldMap[player] = 0
        StatsMap.thirstyMap[player] = 0
    }
}