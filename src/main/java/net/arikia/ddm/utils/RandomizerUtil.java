package net.arikia.ddm.utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class RandomizerUtil {

    public static <T> T getRandomItem(List<T> list) {
        Collections.shuffle(list);
        Random r = new Random();
        return list.get(r.nextInt(list.size() - 1));
    }
}
