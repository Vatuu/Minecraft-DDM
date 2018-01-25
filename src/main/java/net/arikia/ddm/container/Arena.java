package net.arikia.ddm.container;

import com.google.gson.*;
import javafx.util.Pair;
import net.arikia.ddm.DDM;
import net.arikia.ddm.properties.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Arena {

    private final Location loc1;
    private final Location loc2;
    private final String id;
    private final Location dm1_pos;
    private final Location dm2_pos;
    private boolean p1Exists = false;
    private boolean p2Exists = false;
    private DDMPlayer p1;
    private DungeonMaster dm1;
    private DDMPlayer p2;
    private DungeonMaster dm2;

    private GameState state = GameState.NO_PLAYER_WAITING;

    private Map<Pair<Integer, Integer>, DungeonPath> fields = new HashMap<>();

    public Arena(Location loc, String id) {
        loc1 = loc;
        loc2 = new Location(Bukkit.getWorld("world"), loc.getX() + 19, loc.getY(), loc.getZ() + 13);
        dm1_pos = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() + 2, loc.getZ() + 6);
        dm2_pos = new Location(Bukkit.getWorld("world"), loc.getX() + 18, loc.getY() + 2, loc.getZ() + 6);
        this.id = id;
        setupField();
    }

    public void tick() {
        switch (state) {
            case NO_PLAYER_WAITING:
            case PLAYER_1_WAITING:
            case PLAYER_2_WAITING:
            case GAME_START:
            default:
                arePlayerReady();
        }
    }

    private void arePlayerReady() {
        if (p1Exists) {
            if (!p1.getPlayer().getLocation().getBlock().equals(dm1_pos.getBlock())) {
                removeP1();
                Bukkit.broadcastMessage("PLAYER 1 LEFT!");
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getPlayer().getLocation().getBlock().equals(dm1_pos.getBlock())) {
                    setP1(new DDMPlayer(p));
                    Bukkit.broadcastMessage("PLAYER 1 JOINED!");
                    break;
                }
            }
        }
        if (p2Exists) {
            if (!p2.getPlayer().getLocation().getBlock().equals(dm2_pos.getBlock())) {
                removeP2();
                Bukkit.broadcastMessage("PLAYER 2 LEFT!");
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getPlayer().getLocation().getBlock().equals(dm2_pos.getBlock())) {
                    setP2(new DDMPlayer(p));
                    Bukkit.broadcastMessage("PLAYER 2 JOINED!");
                    break;
                }
            }
        }

        if (p1Exists && p2Exists) {
            if (state != GameState.GAME_START)
                Bukkit.broadcastMessage("GAME START!");
            state = GameState.GAME_START;
        } else if (p1Exists && !p2Exists)
            state = GameState.PLAYER_1_WAITING;
        else if (!p1Exists && p2Exists)
            state = GameState.PLAYER_2_WAITING;
        else
            state = GameState.NO_PLAYER_WAITING;
    }

    public String getId() {
        return id;
    }

    public Location getLoc() {
        return loc1;
    }

    private void setP1(DDMPlayer p) {
        p1 = DDM.getPlugin().getPlayerManager().updateArena(p.getPlayer().getUniqueId(), true);
        p1Exists = true;
    }

    private void setP2(DDMPlayer p) {
        p2 = DDM.getPlugin().getPlayerManager().updateArena(p.getPlayer().getUniqueId(), true);
        p2Exists = true;
    }

    private void removeP1() {
        DDM.getPlugin().getPlayerManager().updateArena(p1.getPlayer().getUniqueId(), false);
        p1Exists = false;
        p1.getPlayer().teleport(new Location(Bukkit.getWorld("world"), dm1_pos.getBlockX() - 2, dm1_pos.getBlockY(), dm1_pos.getBlockZ()));
        p1 = null;
    }

    private void removeP2() {
        DDM.getPlugin().getPlayerManager().updateArena(p2.getPlayer().getUniqueId(), false);
        p2Exists = false;
        p2.getPlayer().teleport(new Location(Bukkit.getWorld("world"), dm2_pos.getBlockX() + 2, dm2_pos.getBlockY(), dm2_pos.getBlockZ()));
        p2 = null;
    }

    public boolean removePlayer(UUID uuid) {
        if (p1 != null && uuid == p1.getUUID()) {
            removeP1();
            return true;
        } else if (p2 != null && uuid == p2.getUUID()) {
            removeP2();
            return true;
        } else {
            return false;
        }
    }

    public Map<Pair<Integer, Integer>, DungeonPath> getFields() {
        return fields;
    }

    private void setupField() {
        fields.clear();
        for (int x = 0; x < 19; x++) {
            for (int y = 0; y < 13; y++) {
                if (x == 0 && y == 6) {
                    dm1 = new DungeonMaster(p1, getBlock(x, y));
                    fields.put(new Pair<>(x, y), dm1);
                } else if (x == 18 && y == 6) {
                    dm2 = new DungeonMaster(p2, getBlock(x, y));
                    fields.put(new Pair<>(x, y), dm2);
                } else
                    fields.put(new Pair<>(x, y), new DungeonPath(getBlock(x, y)));
            }
        }
    }

    private Block getBlock(int xOffset, int yOffset) {
        Location lo = new Location(Bukkit.getWorld("world"), loc1.getX() + xOffset, loc1.getY(), loc1.getZ() + yOffset);
        return lo.getBlock();
    }

    public enum PathType {

        NONE,
        BLOCKED,
        BLUE,
        RED
    }

    public static class ArenaSerializer implements JsonSerializer<Arena> {
        public JsonElement serialize(final Arena arena, Type type, final JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add("id", new JsonPrimitive(arena.getId()));
            result.add("x", new JsonPrimitive(arena.getLoc().getX()));
            result.add("y", new JsonPrimitive(arena.getLoc().getY()));
            result.add("z", new JsonPrimitive(arena.getLoc().getZ()));
            return result;
        }
    }

    public class DungeonPath {

        public Block block;
        private PathType type = PathType.NONE;
        private boolean hasMonster = false;
        private CreatureDice monster;

        public DungeonPath(Block b) {
            block = b;
        }

        public PathType getType() {
            return type;
        }

        public Block getBlock() {
            return block;
        }

        public void setPath(boolean player) {
            if (player)
                type = PathType.BLUE;
            else
                type = PathType.RED;
        }

        public void resetPath() {
            type = PathType.NONE;
        }

        public boolean hasMonster() {
            return hasMonster;
        }

        public CreatureDice getMonster() {
            if (hasMonster)
                return monster;
            else
                return null;
        }

        public void setMonster(CreatureDice cd) {
            hasMonster = true;
            monster = cd;
        }
    }

    public class DungeonMaster extends DungeonPath {

        private int hp = 3;
        private DDMPlayer player;

        public DungeonMaster(DDMPlayer p, Block b) {
            super(b);
            player = p;
        }

        public int getHp() {
            return hp;
        }

        public void looseLife() {
            hp -= 1;
        }

        public DDMPlayer getPlayer() {
            return player;
        }
    }
}
