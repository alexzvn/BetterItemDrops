package dev.alexzvn.drop.better.damage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LockedManager {

    protected static HashMap<String,ItemStack> originItems = new HashMap<String,ItemStack>();

    /**
     * Check player can pickup this item or not
     * @param player
     * @param item
     * @return
     */
    public static boolean check(Player player, ItemStack item) {

        List<String> lore = item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<String>();
    
        if (lore.size() < 2) {
            return true;
        }

        String lastLine = lore.get(lore.size() - 1);

        if (! lastLine.startsWith("locked for: ") || lastLine.equals("locked for: ")) {
            return true;
        }

        return lastLine.equals("lcoked for: " + player.getName());
    }

    /**
     * Pull origin item stack
     * @param item
     * @return
     */
    public static ItemStack pull(ItemStack item) {

        List<String> lore = item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<String>();

        if (lore.size() < 2) {
            return item;
        }

        ItemStack origin = originItems.remove(lore.get(lore.size() -2));

        return origin == null ? item : origin;
    }

    public static List<ItemStack> registerDrops(Player player, List<ItemStack> items) {
        List<ItemStack> registed =  new ArrayList<ItemStack>();

        for (ItemStack item : items) {
            registed.add(registerDrop(player, item));
        }

        return registed;
    }

    public static ItemStack registerDrop(Player player, ItemStack item) {
        ItemStack origin = item.clone();

        String uuid = UUID.randomUUID().toString();

        originItems.put(uuid, origin);

        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();

        lore.add(uuid);
        lore.add("locked for: " + player.getName());

        meta.setLore(lore);
        meta.setDisplayName(player.getDisplayName()+ " " + meta.getDisplayName());

        item.setItemMeta(meta);

        return item;
    }
}
