package net.arikia.ddm.container;

import net.arikia.ddm.DDM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaManager {

    private final Map<String, Arena> arenas = new HashMap<>();

    public ArenaManager(DDM p) {
        p.getServer().getScheduler().runTaskTimer(p, () ->
        {
            arenas.values().forEach(Arena::tick);
        }, 0L, 1L);
    }

    public Map<String, Arena> getArenas() {
        return arenas;
    }

    public void addArena(Arena a) {
        arenas.put(a.getId(), a);
    }

    public void removeArena(String id) {
        if (arenas.containsKey(id))
            arenas.remove(id);
    }

    public List<Arena> getAsList() {
        List<Arena> l = new ArrayList<>();
        for (Map.Entry<String, Arena> e : arenas.entrySet()) {
            l.add(e.getValue());
        }
        return l;
    }
}
