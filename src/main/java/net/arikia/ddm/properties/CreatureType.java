package net.arikia.ddm.properties;

import org.bukkit.ChatColor;

public enum CreatureType {

    MONSTER("Normal Monster", ChatColor.GRAY),
    FLYING("Flying Monster", ChatColor.AQUA),
    TUNNEL("Tunneling Monster", ChatColor.DARK_GRAY),
    REACH("Reaching Monster", ChatColor.DARK_AQUA),
    EXODIA("Exodia Part", ChatColor.GOLD);

    private final String name;
    private final ChatColor colour;

    CreatureType(String name, ChatColor colour) {
        this.name = name;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColour() {
        return colour;
    }
}
