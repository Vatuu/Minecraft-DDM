package net.arikia.ddm.gui;

import net.arikia.ddm.container.Dicepool;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GlobalDicepoolUI extends AbstractDiceUI {

    public GlobalDicepoolUI(Dicepool pool) {
        super(pool, false);
    }

    @Override
    public void openUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, dice.getName());
        for (ItemStack is : diceItem)
            inv.addItem(is);
        p.openInventory(inv);
    }
}