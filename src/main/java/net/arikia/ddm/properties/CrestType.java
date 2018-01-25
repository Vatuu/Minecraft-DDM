package net.arikia.ddm.properties;

import org.bukkit.ChatColor;

public enum CrestType {
    MOVE("Movement", ChatColor.AQUA),
    ATTACK("Attack  ", ChatColor.RED),
    DEFENSE("Defense ", ChatColor.GREEN),
    SPELL("Spell   ", ChatColor.LIGHT_PURPLE),
    TRAP("Trap    ", ChatColor.DARK_PURPLE),
    SUMMON1("Summon ➀", ChatColor.GOLD),
    SUMMON2("Summon ➁", ChatColor.GOLD),
    SUMMON3("Summon ➂", ChatColor.GOLD),
    SUMMON4("Summon ➃", ChatColor.GOLD);

    private final String name;
    private final ChatColor colour;

    CrestType(String name, ChatColor colour) {
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
