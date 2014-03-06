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

    private double jumpVelocity = 0.5;

    private double horizontalVelocity = 0.09;

    private double gravitationalConstant = -0.05;

    private Vector vertialVelocity = new Vector();

    public FCPlayerControllerTask(FlappyChicken instance, FCPlayer fcPlayer) {
        plugin = instance;
        this.fcPlayer = fcPlayer;
    }

    @Override
    public void run() {
        Chicken chicken = fcPlayer.getChicken();
        if(fcPlayer.getIsJump()) {
            vertialVelocity = vertialVelocity.setY(jumpVelocity);
            fcPlayer.unJumpChicken();
        }
        chicken.setVelocity(vertialVelocity.setZ(horizontalVelocity));
        vertialVelocity = vertialVelocity.add(new Vector(0, gravitationalConstant, 0));

        Location chickenLocation = chicken.getLocation();
        Location playerOffset = chickenLocation.add(-10, 0, 5).setDirection(new Vector(1, 0, 0));
        playerOffset.setY(85);
        fcPlayer.getPlayer().teleport(playerOffset);
    }
}
