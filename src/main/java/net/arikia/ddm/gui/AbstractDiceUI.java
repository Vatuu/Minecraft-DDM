package net.arikia.ddm.gui;

import net.arikia.ddm.container.CreatureDice;
import net.arikia.ddm.container.DiceSide;
import net.arikia.ddm.container.Dicepool;
import net.arikia.ddm.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDiceUI {

    public Dicepool dice;
    public List<ItemStack> diceItem = new ArrayList<>();
    public List<ItemStack> pageBar = new ArrayList<>();
    public boolean interactable;

    public AbstractDiceUI(Dicepool dice, boolean interactable) {
        this.dice = dice;
        renderAllDice();
        createPageBar();
        this.interactable = interactable;
    }

    private ItemStack renderDiceItem(CreatureDice cd) {
        ItemStack is = new ItemStack(Material.DIAMOND_HOE, 1, (short) cd.getModel());
        ItemMeta im = is.getItemMeta();
        im.setUnbreakable(true);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        im.setDisplayName(TextUtils.color("§7§lDDM Dice | " + cd.getCClass().getColour() + cd.getName()));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(TextUtils.color("§7Level " + cd.getLevel() + " | " + cd.getCClass().getColour() + cd.getCClass().getName() + " §7| " + cd.getType().getColour() + cd.getType().getName()));
        lore.add("");
        StringBuilder hp = new StringBuilder().append("§4§l");
        for (int i = 0; i < cd.getHP(); i++)
            hp.append("♥ ");
        lore.add(TextUtils.color(hp.toString()));
        lore.add("§c§lATK: " + cd.getATK());
        lore.add("§3§lDEF: " + cd.getDEF());
        lore.add("");
        for (DiceSide s : cd.getDice().getSides()) {
            if (s.getCount() > 1)
                lore.add(s.getCrest().getColour() + s.getCrest().getName() + " x" + s.getCount());
            else
                lore.add(s.getCrest().getColour() + s.getCrest().getName());
        }
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    private void renderAllDice() {
        for (CreatureDice cd : dice.getDice()) {
            diceItem.add(renderDiceItem(cd));
        }
    }

    private void createPageBar() {
        ItemStack window = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 12);
        ItemMeta im = window.getItemMeta();
        im.setDisplayName(" ");
        window.setItemMeta(im);
        for (int i = 0; i < 9; i++)
            pageBar.add(window);
    }

    public boolean isInteractable() {
        return interactable;
    }

    public Dicepool getDicepool() {
        return dice;
    }

    public abstract void openUI(Player p);

}
