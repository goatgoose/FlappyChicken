package com.goatgoose.flappychicken;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class FlappyChicken extends JavaPlugin {

    @Override
    public void onEnable() {

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

}
