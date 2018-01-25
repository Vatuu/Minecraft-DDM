package net.arikia.ddm.container;

import org.bukkit.entity.Player;

import java.util.UUID;

public class DDMPlayer {

    private Player player;
    private UUID uuid;
    private String displayName;

    private boolean isInArena = false;

    public DDMPlayer(Player p) {
        isInArena = false;
        this.player = p;
        uuid = p.getUniqueId();
        displayName = p.getDisplayName();
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isInArena() {
        return isInArena;
    }

    public void setInArena(boolean b) {
        isInArena = b;
    }
}