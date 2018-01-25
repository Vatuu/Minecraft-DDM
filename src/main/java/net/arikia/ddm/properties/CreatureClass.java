package net.arikia.ddm.properties;

import org.bukkit.ChatColor;

public enum CreatureClass {
    WARRIOR("Warrior", ChatColor.BLUE),
    DRAGON("Dragon", ChatColor.DARK_RED),
    SPELLCASTER("Spellcaster", ChatColor.GRAY),
    ZOMBIE("Zombie", ChatColor.YELLOW),
    BEAST("Beast", ChatColor.DARK_GREEN),
    ITEM("Item", ChatColor.BLACK);

    private final String name;
    private final ChatColor colour;

    CreatureClass(String name, ChatColor colour) {
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
