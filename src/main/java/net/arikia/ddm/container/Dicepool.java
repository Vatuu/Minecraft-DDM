package net.arikia.ddm.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dicepool {

    private List<CreatureDice> dice = new ArrayList<>();
    private String name;

    public Dicepool(String name, CreatureDice... dice) {
        this.name = name;
        Collections.addAll(this.dice, dice);
    }

    public Dicepool(String name, List<CreatureDice> list) {
        this.name = name;
        dice.addAll(list);
    }

    public List<CreatureDice> getDice() {
        return dice;
    }

    public String getName() {
        return name;
    }

    public boolean hasDice(CreatureDice dice) {
        return this.dice.contains(dice);
    }

    public void removeDice(CreatureDice cd) {
        dice.remove(cd);
    }

    public void addDice(CreatureDice cd) {
        dice.add(cd);
    }

    public double getAverageMonsterLevel() {
        double i = 0;
        for (CreatureDice cd : dice)
            i += cd.getLevel();
        return i / dice.size();
    }
}
