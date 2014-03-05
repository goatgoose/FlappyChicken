package com.goatgoose.flappychicken.Tasks;

import com.goatgoose.flappychicken.FlappyChicken;
import com.goatgoose.flappychicken.Model.FCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FCPlayerControllerTask extends BukkitRunnable {

    private FlappyChicken plugin;

    private FCPlayer fcPlayer;

    public FCPlayerControllerTask(FlappyChicken instance, FCPlayer fcPlayer) {
        plugin = instance;
        this.fcPlayer = fcPlayer;
    }

    @Override
    public void run() {
        Chicken chicken = fcPlayer.getChicken();
        if(fcPlayer.getIsJump()) {
            chicken.setVelocity(new Vector(0, 2, 0.05));
            fcPlayer.unJumpChicken();
        } else {
            chicken.setVelocity(new Vector(0, -0.05, 0.05));
        }

        Location chickenLocation = chicken.getLocation();
        Location playerOffset = chickenLocation.add(-10, 0, 5).setDirection(new Vector(1, 0, 0));
        fcPlayer.getPlayer().teleport(playerOffset);
    }
}
