package net.arikia.ddm.container;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private static Map<UUID, DDMPlayer> players = new HashMap<>();

    public void addPlayer(Player p) {
        if (!players.containsKey(p.getUniqueId()))
            players.put(p.getUniqueId(), new DDMPlayer(p));
    }

    public void removePlayer(DDMPlayer p) {
        if (players.containsValue(p))
            players.remove(p.getUUID());
    }

    public DDMPlayer getPlayer(String name) {
        return players.get(Bukkit.getPlayer(name).getUniqueId());
    }

    public DDMPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public DDMPlayer getPlayer(Player p) {
        return players.get(p.getUniqueId());
    }

    public DDMPlayer updateArena(UUID uuid, boolean b) {
        players.get(uuid).setInArena(b);
        return players.get(uuid);
    }
}
