package dev.alexzvn.drop.better.damage;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DamageManager {

    protected static HashMap<Integer,EntityDamaged> monitorDamage = new HashMap<Integer,EntityDamaged>();

    public static EntityDamaged pull(Entity entity) {
        return monitorDamage.remove(entity.getEntityId());
    }

    public static void put(Entity entity, Player player, double damage) {
        
        if (monitorDamage.get(entity.getEntityId()) == null) {
            monitorDamage.put(entity.getEntityId(), new EntityDamaged());
        }

        monitorDamage.get(entity.getEntityId()).increase(player, damage);
    }
}
