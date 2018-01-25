package net.arikia.ddm.container;

import net.arikia.ddm.properties.CrestType;

public class DiceSide {

    private CrestType crest;
    private int count;

    public DiceSide(CrestType crest, int count) {
        this.crest = crest;
        this.count = count;
    }

    public CrestType getCrest() {
        return crest;
    }

    public int getCount() {
        return count;
    }
}