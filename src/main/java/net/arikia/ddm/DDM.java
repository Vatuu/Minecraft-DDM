package net.arikia.ddm;

import com.google.gson.JsonArray;
import net.arikia.ddm.container.*;
import net.arikia.ddm.events.BukkitListener;
import net.arikia.ddm.gui.GlobalDicepoolUI;
import net.arikia.ddm.utils.JSONFileUtil;
import net.arikia.ddm.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Map;

public final class DDM extends JavaPlugin implements Listener {

    private static DDM p;

    private Dicepool globalPool;
    private ArenaManager aManager;
    private PlayerManager pManager;

    public static DDM getPlugin() {
        return p;
    }

    public List<CreatureDice> getDiceList() {
        return globalPool.getDice();
    }

    @Override
    public void onEnable() {
        p = this;

        List<CreatureDice> dice = JSONFileUtil.parseCreatureDice(getTextResource("defaultDice.json"));
        if (dice == null)
            Bukkit.getLogger().severe("UNABLE TO PARSE DICE!");

        globalPool = new Dicepool(TextUtils.color("&2&lGlobal Dicepool"), dice);
        setupArenas();
        pManager = new PlayerManager();

        for (Player p : Bukkit.getOnlinePlayers()) {
            pManager.addPlayer(p);
        }

        Bukkit.getPluginManager().registerEvents(new BukkitListener(), this);
    }


    @Override
    public void onDisable() {
        saveArenas();
        p = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equals("ddm")) {
            if (args[0].equalsIgnoreCase("getDice")) {
                GlobalDicepoolUI ui = new GlobalDicepoolUI(globalPool);
                ui.openUI((Player) sender);
                return true;
            } else if (args[0].equalsIgnoreCase("testArena")) {
                Arena a = new Arena(((Player) sender).getLocation(), "test");
                for (Map.Entry e : a.getFields().entrySet()) {
                    if (e.getValue() instanceof Arena.DungeonMaster) {
                        ((Arena.DungeonMaster) e.getValue()).getBlock().getRelative(0, 1, 0).setType(Material.REDSTONE_BLOCK);
                        continue;
                    }
                    ((Arena.DungeonPath) e.getValue()).block.setType(Material.ICE);
                }
                aManager.addArena(a);
            } else if (args[0].equalsIgnoreCase("leaveArena")) {
                DDMPlayer p = pManager.getPlayer((Player) sender);
                if (!p.isInArena()) {
                    p.getPlayer().sendMessage("You're not in a arena!");
                    return true;
                }
                for (Map.Entry e : aManager.getArenas().entrySet()) {
                    boolean b = ((Arena) e.getValue()).removePlayer(p.getUUID());
                    if (b) {
                        p.setInArena(false);
                        p.getPlayer().sendMessage("You were removed from the arena.");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public PlayerManager getPlayerManager() {
        return pManager;
    }

    public ArenaManager getArenaManager() {
        return aManager;
    }

    private void setupArenas() {
        aManager = new ArenaManager(this);
        try {
            String s = JSONFileUtil.readFromFile(new File(getDataFolder(), "arena.json"));
            List<Arena> arenas = JSONFileUtil.deserializeArenas(s);
            if (arenas.isEmpty()) {
                Bukkit.getLogger().warning("[DDM] No Arenas found to parse.");
            } else {
                Bukkit.getLogger().warning("Found already existing Arenas, parsing...");
                for (Arena a : arenas) {
                    Bukkit.getLogger().fine("-> Found Arena: " + a.getId());
                    aManager.addArena(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveArenas() {
        Bukkit.getLogger().info("[DDM] ---------- Arena Management ----------");
        if (aManager.getArenas().isEmpty()) {
            Bukkit.getLogger().fine("      > No Arenas found to save.");
        } else {
            Bukkit.getLogger().info("      > Found Arenas to save...");
            try {
                JsonArray arenas = JSONFileUtil.serializeArenas(aManager.getAsList());
                JSONFileUtil.writeToFile(new File(getDataFolder(), "arena.json"), arenas.getAsJsonArray().getAsString());
                for(Arena a : aManager.getAsList())
                    Bukkit.getLogger().fine("      >> ID: " + a.getId() + " | X: " + a.getLoc().getBlockX() + " | Y: " + a.getLoc().getBlockY() + " | Z: " + a.getLoc().getBlockZ());
            } catch (Exception e) {
                Bukkit.getLogger().severe("      > A Error occurred while saving registered Arenas!");
                e.printStackTrace();
            } finally {

            }
        }
        Bukkit.getLogger().info("[DDM] --------------------------------------");
    }
}
