package dev.alexzvn.drop.better.listeners;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import dev.alexzvn.drop.better.damage.DamageManager;
import dev.alexzvn.drop.better.damage.EntityDamaged;
import dev.alexzvn.drop.better.damage.LockedManager;

public class HandleDropItems implements Listener {

    protected HashMap<String,Long> lastAlert = new HashMap<String,Long>();
    
    @EventHandler
    public void registerDropsForEntity(EntityDeathEvent event) {

        EntityDamaged damageCount = DamageManager.pull(event.getEntity());

        if (damageCount == null || damageCount.topDamager() == null){
            return;
        }

        List<ItemStack> drops = LockedManager.registerDrops(damageCount.topDamager(), event.getDrops());

        event.getDrops().clear();

        for (ItemStack itemStack : drops) {
            event.getDrops().add(itemStack);
        }
    }

    @EventHandler
    public void handlePlayerPickupItem(EntityPickupItemEvent event) {
        
        if (! (event.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) event.getEntity();

        if (! LockedManager.check(p, event.getItem().getItemStack())) {
            event.setCancelled(true);
            alert(p); return;
        }

        event.getItem().setItemStack(
            LockedManager.pull(event.getItem().getItemStack())
        );
    }

    protected boolean shouldAlert(Player player) {
        Long time = lastAlert.get(player.getName());

        if (time == null) {
            return true;
        }

        return (time + 3000) < System.currentTimeMillis();
    }

    protected void alert(Player player) {
        if (shouldAlert(player)) {
            return;
        }

        player.sendMessage(
            ChatColor.translateAlternateColorCodes('&', "&a This item is not your")
        );

        lastAlert.put(player.getName(), System.currentTimeMillis());
    }
}
