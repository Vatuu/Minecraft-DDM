package net.arikia.ddm.container;

import net.arikia.ddm.properties.CreatureClass;
import net.arikia.ddm.properties.CreatureType;

public class CreatureDice {

    private int model;
    private DiceProperty dice;
    private CreatureType type;
    private CreatureClass cType;
    private String name;
    private int level;
    private int HP;
    private int ATK;
    private int DEF;

    public CreatureDice(int model, DiceProperty dice, CreatureType type, CreatureClass cType, String name, int level, int HP, int ATK, int DEF) {
        this.model = model;
        this.dice = dice;
        this.type = type;
        this.cType = cType;
        this.name = name;
        this.level = level;
        this.HP = HP;
        this.ATK = ATK;
        this.DEF = DEF;
    }

    public int getModel() {
        return model;
    }

    public DiceProperty getDice() {
        return dice;
    }

    public CreatureType getType() {
        return type;
    }

    public CreatureClass getCClass() {
        return cType;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHP() {
        return HP;
    }

    public int getATK() {
        return ATK;
    }

    public int getDEF() {
        return DEF;
    }
}
