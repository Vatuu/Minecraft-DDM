package net.arikia.ddm.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiceProperty {

    private List<DiceSide> sides = new ArrayList<>();

    public DiceProperty(List<DiceSide> sides) {
        this.sides = sides;
    }

    public DiceProperty(DiceSide... sides) {
        Collections.addAll(this.sides);
    }

    public List<DiceSide> getSides() {
        return sides;
    }
}
