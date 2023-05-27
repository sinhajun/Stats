package io.github.sinhajun.changeStats

import io.github.sinhajun.StatsMap
import io.github.sinhajun.stats
import org.bukkit.Bukkit
import org.bukkit.block.Biome
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

class Change: Listener {

    private val polarClimate = arrayListOf(Biome.SNOWY_TAIGA, Biome.FROZEN_RIVER, Biome.SNOWY_PLAINS, Biome.ICE_SPIKES, Biome.SNOWY_BEACH)
    private val coldClimate = arrayListOf(Biome.WINDSWEPT_HILLS, Biome.WINDSWEPT_FOREST, Biome.WINDSWEPT_GRAVELLY_HILLS, Biome.STONY_SHORE, Biome.TAIGA, Biome.OLD_GROWTH_PINE_TAIGA, Biome.OLD_GROWTH_SPRUCE_TAIGA)
    private val temperateClimate = arrayListOf(Biome.RIVER, Biome.FOREST, Biome.FLOWER_FOREST, Biome.BIRCH_FOREST, Biome.OLD_GROWTH_BIRCH_FOREST, Biome.DARK_FOREST, Biome.PLAINS, Biome.SUNFLOWER_PLAINS, Biome.SWAMP, Biome.MANGROVE_SWAMP, Biome.BEACH, Biome.MUSHROOM_FIELDS)
    private val tropicalClimate = arrayListOf(Biome.JUNGLE, Biome.SPARSE_JUNGLE, Biome.BAMBOO_JUNGLE)
    private val dryClimate = arrayListOf(Biome.SAVANNA, Biome.SAVANNA_PLATEAU, Biome.WINDSWEPT_SAVANNA, Biome.DESERT, Biome.BADLANDS, Biome.WOODED_BADLANDS, Biome.ERODED_BADLANDS)

    @EventHandler
     fun change(event: PlayerJoinEvent) {
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach {player ->
                    if (player != null) {
                        climateChange(player)
                    }
                }
            }
        }.runTaskTimer(stats, ((10..25).random() * 20).toLong(), ((10..25).random() * 20).toLong())

        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach {player ->
                    if (player != null) {
                        if (StatsMap.hotMap[player]!! < 0) StatsMap.hotMap[player] = 0
                        if (StatsMap.coldMap[player]!! < 0) StatsMap.coldMap[player] = 0
                        if (StatsMap.thirstyMap[player]!! < 0) StatsMap.thirstyMap[player] = 0
                    }
                }
            }
        }
    }

    private fun climateChange(player: Player) {
        val biome = player.world.getBiome(player.location.add(0.0, 0.0, 0.0))

        when {
            polarClimate.contains(biome) -> { // 한대기후
                val hotRandomValue = (1..4).random()
                val coldRandomValue = (1..4).random()
                val thirstyRandomValue = (1..2).random()

                StatsMap.hotMap[player] = StatsMap.hotMap[player]!! - hotRandomValue
                StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
                StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + thirstyRandomValue
            }
            coldClimate.contains(biome) -> { // 냉대기후
                val hotRandomValue = (1..3).random()
                val coldRandomValue = (1..3).random()

                StatsMap.hotMap[player] = StatsMap.hotMap[player]!! - hotRandomValue
                StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
                StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + 1
            }
            temperateClimate.contains(biome) -> { // 온대기후
                val hotRandomValue = (1..2).random()
                val coldRandomValue = (1..2).random()

                StatsMap.hotMap[player] = StatsMap.hotMap[player]!! - hotRandomValue
                StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
                StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + 1
            }
            tropicalClimate.contains(biome) -> { // 열대기후
                val hotRandomValue = (1..3).random()
                val coldRandomValue = (1..3).random()

                StatsMap.hotMap[player] = StatsMap.hotMap[player]!! - hotRandomValue
                StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
                if (StatsMap.thirstyMap[player]!! + 1 <= 40) StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + 1
            }
            dryClimate.contains(biome) -> { // 건조기후
                val hotRandomValue = (1..4).random()
                val coldRandomValue = (1..4).random()
                val thirstyRandomValue = (1..3).random()

                StatsMap.hotMap[player] = StatsMap.hotMap[player]!! - hotRandomValue
                StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
                StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + thirstyRandomValue
            }
        }
    }

    inner class Drink: Listener
}