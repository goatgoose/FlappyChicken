package com.goatgoose.flappychicken.Model;

import com.goatgoose.flappychicken.FlappyChicken;
import com.goatgoose.flappychicken.Tasks.FCPlayerControllerTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class FCPlayer {

    private FlappyChicken plugin;

    private FCPlayerControllerTask controller;

    private Player player;

    private Chicken chicken;

    private boolean isJump = false;

    public FCPlayer(FlappyChicken instance, Player player) {
        plugin = instance;
        this.player = player;
        this.chicken = (Chicken) player.getWorld().spawnEntity(new Location(player.getWorld(), 6, 126, 49), EntityType.CHICKEN);
        controller = new FCPlayerControllerTask(plugin, this);

        for(FCPlayer fcPlayer : plugin.getFlappyChickenPlayers()) {
            player.hidePlayer(fcPlayer.getPlayer());
        }

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, controller, 0, 1);
    }

    public Player getPlayer() {
        return player;
    }

    public Chicken getChicken() {
        return chicken;
    }

    public boolean getIsJump() {
        return isJump;
    }

    public void jumpChicken() {
        isJump = true;
    }

    public void unJumpChicken() {
        isJump = false;
    }
}
