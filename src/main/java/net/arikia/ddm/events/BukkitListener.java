package net.arikia.ddm.events;

import net.arikia.ddm.DDM;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class BukkitListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();

        if (DDM.getPlugin().getPlayerManager().getPlayer(p).isInArena()) {
            if (event.getPlayer().isOnGround()) {
                p.setWalkSpeed(0);
            }
        } else {
            p.setWalkSpeed(0.2f);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        DDM.getPlugin().getPlayerManager().addPlayer(p);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        DDM.getPlugin().getPlayerManager().removePlayer(DDM.getPlugin().getPlayerManager().getPlayer(p));
    }
}
