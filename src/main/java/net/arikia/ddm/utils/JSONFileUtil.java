package net.arikia.ddm.utils;

import com.google.gson.*;
import net.arikia.ddm.container.Arena;
import net.arikia.ddm.container.CreatureDice;
import net.arikia.ddm.container.DiceProperty;
import net.arikia.ddm.container.DiceSide;
import net.arikia.ddm.properties.CreatureClass;
import net.arikia.ddm.properties.CreatureType;
import net.arikia.ddm.properties.CrestType;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class JSONFileUtil {

    public static List<CreatureDice> parseCreatureDice(Reader reader) {
        List<CreatureDice> dices = new ArrayList<>();
        JsonArray array = (new JsonParser()).parse(reader).getAsJsonArray();
        for (JsonElement jsonElement : array) {
            JsonElement je = jsonElement.getAsJsonObject().get("dm");
            int model = je.getAsJsonObject().get("model").getAsInt();
            DiceProperty dice = parseDiceProperty(je);
            CreatureType type = CreatureType.valueOf(je.getAsJsonObject().get("type").getAsString());
            CreatureClass cclass = CreatureClass.valueOf(je.getAsJsonObject().get("class").getAsString());
            String name = je.getAsJsonObject().get("name").getAsString();
            int level = je.getAsJsonObject().get("level").getAsInt();
            int hp = je.getAsJsonObject().get("hp").getAsInt();
            int atk = je.getAsJsonObject().get("atk").getAsInt();
            int def = je.getAsJsonObject().get("def").getAsInt();
            dices.add(new CreatureDice(model, dice, type, cclass, name, level, hp, atk, def));
        }
        return dices;
    }

    public static String readFromFile(File file) throws IOException {
        file.getParentFile().mkdirs();

        if (!file.exists())
            file.createNewFile();
        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }

    public static void writeToFile(File file, String s) throws IOException {
        file.getParentFile().mkdirs();

        if (!file.exists())
            file.createNewFile();
        FileUtils.writeStringToFile(file, s, Charset.defaultCharset(), false);
    }

    public static JsonArray serializeArenas(List<Arena> arenas) {
        JsonArray array = new JsonArray();

        if (arenas.isEmpty())
            return array;

        Gson gson = new GsonBuilder().registerTypeAdapter(Arena.class, new Arena.ArenaSerializer()).setPrettyPrinting().create();
        for (Arena a : arenas) {
            array.add(gson.toJson(a));
        }

        return array;
    }

    public static List<Arena> deserializeArenas(String s) {
        List<Arena> result = new ArrayList<>();
        JsonArray array = (new JsonParser()).parse(s).getAsJsonArray();
        for (JsonElement e : array) {
            String id = e.getAsJsonObject().get("id").getAsString();
            Location l = new Location(Bukkit.getWorld("world"), e.getAsJsonObject().get("x").getAsFloat(), e.getAsJsonObject().get("y").getAsFloat(), e.getAsJsonObject().get("z").getAsFloat());
            result.add(new Arena(l, id));
        }
        return result;
    }

    private static DiceProperty parseDiceProperty(JsonElement element) {
        List<DiceSide> sides = new ArrayList<>();
        JsonArray dice = element.getAsJsonObject().getAsJsonArray("dice");
        for (JsonElement je : dice)
            sides.add(new DiceSide(CrestType.valueOf(je.getAsJsonObject().get("crest").getAsString()), je.getAsJsonObject().get("count").getAsInt()));
        return new DiceProperty(sides);
    }
}
