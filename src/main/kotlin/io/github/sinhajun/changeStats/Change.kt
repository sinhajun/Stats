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
        }.runTaskTimer(stats, 1L, 0L)
    }

    private fun climateChange(player: Player) {
        if (polarClimate.contains(player.world.getBiome(player.location.add(0.0, 0.0, 0.0)))) {
            val hotRandomValue = (3..4).random()
            val coldRandomValue = (3..4).random()

            if (StatsMap.hotMap[player]!! - hotRandomValue > 0) StatsMap.hotMap[player] = StatsMap.hotMap[player]!! - hotRandomValue
            if (StatsMap.coldMap[player]!! + coldRandomValue < 40) StatsMap.coldMap[player] = StatsMap.coldMap[player]!! + coldRandomValue
            if (StatsMap.thirstyMap[player]!! + 1 <= 40) StatsMap.thirstyMap[player] = StatsMap.thirstyMap[player]!! + 1

        }
    }

    inner class Drink: Listener
}