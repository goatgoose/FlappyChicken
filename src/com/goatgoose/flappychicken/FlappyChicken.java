package com.goatgoose.flappychicken;

import com.goatgoose.flappychicken.Listeners.PlayerListener;
import com.goatgoose.flappychicken.Model.FCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class FlappyChicken extends JavaPlugin {

    private PlayerListener playerListener;

    private List<FCPlayer> flappyChickenPlayers = new ArrayList<FCPlayer>();

        @Override
    public void onEnable() {
        playerListener = new PlayerListener(this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Player player = (Player) sender;
        // temp testing
        if(command.getName().equalsIgnoreCase("test")) {
            final Chicken testChicken = (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    testChicken.setVelocity(new Vector(0, 0.2, -0.3));
                }
            }, 0, 1);
            return true;
        }
        return false;
    }

    public List<FCPlayer> getFlappyChickenPlayers() {
        return flappyChickenPlayers;
    }

    public void addFlappyChickenPlayer(Player player) {
        flappyChickenPlayers.add(new FCPlayer(this, player));
    }

    public void removeFlappyChickenPlayer(Player player) {
        for(FCPlayer fcPlayer : flappyChickenPlayers) {
            if(player == fcPlayer.getPlayer()) {
                flappyChickenPlayers.remove(fcPlayer);
                return;
            }
        }
    }

    public FCPlayer getFCPlayer(Player player) {
        for(FCPlayer fcPlayer : flappyChickenPlayers) {
            if(player == fcPlayer.getPlayer()) {
                return fcPlayer;
            }
        }
        return null;
    }

}
