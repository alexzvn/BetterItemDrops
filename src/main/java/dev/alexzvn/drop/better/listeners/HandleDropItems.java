package dev.alexzvn.drop.better.listeners;

import java.util.List;

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

        if (! LockedManager.check((Player) event.getEntity(), event.getItem().getItemStack())) {
            event.setCancelled(true);
            event.getEntity().sendMessage("this suff dont belong with you");

            return;
        }

        event.getItem().setItemStack(
            LockedManager.pull(event.getItem().getItemStack())
        );
    }
}
