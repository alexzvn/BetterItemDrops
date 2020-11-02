package dev.alexzvn.drop.better.damage;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EntityDamaged {

    protected HashMap<String,Double> playerDamage = new HashMap<String,Double>();

    public void increase(Player player, double damage) {

        String id = player.getName();

        if (playerDamage.get(id) == null) {
            playerDamage.put(id, damage); return;
        }

        playerDamage.put(id, playerDamage.get(id) + damage);
    }

    public Player topDamager() {
        String topPlayer = null;
        double biggerDamage = 0;

        for (String id : playerDamage.keySet()) {
            double currentDamage = playerDamage.get(id);
            boolean shouldChange = currentDamage > biggerDamage;

            biggerDamage = shouldChange ? currentDamage : biggerDamage;
            topPlayer = shouldChange ? id : topPlayer;
        }

        if (topPlayer == null) {
            return null;
        }

        return Bukkit.getPlayer(topPlayer);
    }

    public HashMap<String,Double> getListDamager() {
        return playerDamage;
    }
}
