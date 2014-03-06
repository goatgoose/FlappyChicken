package com.goatgoose.flappychicken;

import com.goatgoose.flappychicken.Listeners.PlayerListener;
import com.goatgoose.flappychicken.Model.FCPlayer;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import org.bukkit.Server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlappyChicken extends JavaPlugin {

    private PlayerListener playerListener;

    private List<FCPlayer> flappyChickenPlayers = new ArrayList<FCPlayer>();

    private WorldEditPlugin worldEdit;

    private int currentHorizontalMapOffset = 0;

    @Override
    public void onEnable() {
        playerListener = new PlayerListener(this);

        List<Integer> possiblePipePlacements = new ArrayList<Integer>();
        for(int i = 2; i <= 15; i++) {
            possiblePipePlacements.add(i);
        }

        for(int i = 0; i < 4; i++) {
            loadArea("section", 0, 70, currentHorizontalMapOffset);
            currentHorizontalMapOffset = currentHorizontalMapOffset + 19;
        }
        for(int i = 0; i < 10; i++) {
            loadArea("section_with_pipe", 0, 70, currentHorizontalMapOffset);
            loadArea("pipe_opening", 4, 117 + possiblePipePlacements.get(new Random().nextInt(possiblePipePlacements.size())), currentHorizontalMapOffset + 7);
            currentHorizontalMapOffset = currentHorizontalMapOffset + 19;
        }
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

    private void loadArea(String schematicName, int x, int y, int z) {
        File schematicFile = new File(this.getDataFolder(), "schematics/" + schematicName + ".schematic");
        String formatName = "mcedit";

        if (!schematicFile.exists()) {
            getLogger().info("Could not find schematic at " + schematicFile.getAbsolutePath());
            return;
        }

        SchematicFormat format = SchematicFormat.getFormat(formatName);
        if (format == null) {
            getLogger().info("Unknown schematic format: " + formatName);
            return;
        }

        try {
            CuboidClipboard clip = format.load(schematicFile);
            EditSession session = new EditSession(BukkitUtil.getLocalWorld(Bukkit.getServer().getWorld("FlappyChickenWorld")), 500000);
            clip.place(session, new com.sk89q.worldedit.Vector(x, y, z), false);

            getLogger().info("Placed schematic " + schematicFile.getAbsolutePath());
        } catch (DataException e) {
            getLogger().info("Load error: " + e.getMessage());
        } catch (IOException e) {
            getLogger().info("Schematic could not read or it does not exist: " + e.getMessage());
        } catch (MaxChangedBlocksException e) {
            getLogger().info("Max blocks changed: " + e.getMessage());
        }
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
