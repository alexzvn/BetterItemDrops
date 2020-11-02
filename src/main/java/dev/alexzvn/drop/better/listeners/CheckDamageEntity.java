package dev.alexzvn.drop.better.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import dev.alexzvn.drop.better.damage.DamageManager;

public class CheckDamageEntity implements Listener {

    @EventHandler
    public static void checkDamage(EntityDamageByEntityEvent event) {

        if (
            ! (event.getDamager() instanceof Player) ||
            event.getEntity() instanceof Player
        ) return;

        DamageManager.put(
            event.getEntity(),
            (Player) event.getDamager(),
            event.getDamage()
        );
    }
}
