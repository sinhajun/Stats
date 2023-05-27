package io.github.sinhajun.changeStats

import io.github.sinhajun.StatsMap
import io.github.sinhajun.climate.*
import io.github.sinhajun.stats
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

class Change: Listener {

    private val maxStat: Int = 50

    @EventHandler
     fun change(event: PlayerJoinEvent) {
         stats.server.pluginManager.registerEvents(Drink(), stats)
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach { player ->
                    if (player != null) {
                        climateChange(player)
                        playerInventoryItemChange(player)
                        blockChange(player)
                    }
                }
            }
        }.runTaskTimer(stats, ((10..25).random() * 20).toLong(), ((10..25).random() * 20).toLong())

        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach {player ->
                    if (player != null) {
                        if (StatsMap.hotMap[player]!! < 0) StatsMap.hotMap[player] = 0 else if (StatsMap.hotMap[player]!! > maxStat) StatsMap.hotMap[player] = 40
                        if (StatsMap.coldMap[player]!! < 0) StatsMap.coldMap[player] = 0 else if (StatsMap.coldMap[player]!! > maxStat) StatsMap.coldMap[player] = 40
                        if (StatsMap.thirstyMap[player]!! < 0) StatsMap.thirstyMap[player] = 0 else if (StatsMap.thirstyMap[player]!! > maxStat) StatsMap.thirstyMap[player] = 40
                    }
                }
            }
        }.runTaskTimer(stats, 0L, 0L)
    }

    private fun climateChange(player: Player) {
        val biome = player.world.getBiome(player.location.add(0.0, 0.0, 0.0))

        when {
            polarClimate.contains(biome) -> { // 한대기후
                val hotRandomValue = (0..4).random()
                val coldRandomValue = (0..4).random()
                val thirstyRandomValue = (0..2).random()

                StatsMap.hotMap[player] = StatsMap.hotMap[player]!! - hotRandomValue
                StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
                StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + thirstyRandomValue
            }
            coldClimate.contains(biome) -> { // 냉대기후
                val hotRandomValue = (-3..2).random()
                val coldRandomValue = (0..3).random()

                StatsMap.hotMap[player] = StatsMap.hotMap[player]!! + hotRandomValue
                StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
                StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + 1
            }
            temperateClimate.contains(biome) -> { // 온대기후
                val hotRandomValue = (0..2).random()
                val coldRandomValue = (0..2).random()

                StatsMap.hotMap[player] = StatsMap.hotMap[player]!! + hotRandomValue
                StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
                StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + 1
            }
            tropicalClimate.contains(biome) -> { // 열대기후
                val hotRandomValue = (0..3).random()
                val coldRandomValue = (-3..2).random()

                StatsMap.hotMap[player] = StatsMap.hotMap[player]!! + hotRandomValue
                StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
                if (StatsMap.thirstyMap[player]!! + 1 <= 40) StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + 1
            }
            dryClimate.contains(biome) -> { // 건조기후
                val hotRandomValue = (0..4).random()
                val coldRandomValue = (0..4).random()
                val thirstyRandomValue = (0..3).random()

                StatsMap.hotMap[player] = StatsMap.hotMap[player]!! - hotRandomValue
                StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
                StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + thirstyRandomValue
            }
        }
    }

    private fun playerInventoryItemChange(player: Player) {
        if (player.inventory.itemInMainHand.type == Material.REDSTONE_TORCH) {
            StatsMap.hotMap[player] = StatsMap.hotMap[player]!! + (0..2).random()
            StatsMap.coldMap[player] = StatsMap.coldMap[player]!! - (3..5).random()
        } else if (player.inventory.itemInMainHand.type == Material.TORCH) {
            StatsMap.hotMap[player] = StatsMap.hotMap[player]!! + (0..2).random()
            StatsMap.coldMap[player] = StatsMap.coldMap[player]!! - (2..4).random()
        }
    }

    private fun blockChange(player: Player) {
        if (player.location.block.type == Material.WATER) {
            StatsMap.hotMap[player] = StatsMap.hotMap[player]!! - (3..5).random()
        }
    }

    inner class Drink: Listener {
        @EventHandler
        fun onDrink(event: PlayerItemConsumeEvent) {
            if (event.item.type == Material.MILK_BUCKET || event.item.type == Material.POTION) {
                val player = event.player
                StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! - (10..15).random()
            }
        }
    }
}