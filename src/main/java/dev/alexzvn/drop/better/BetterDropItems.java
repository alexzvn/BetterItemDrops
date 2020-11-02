package dev.alexzvn.drop.better;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import dev.alexzvn.drop.better.listeners.CheckDamageEntity;
import dev.alexzvn.drop.better.listeners.HandleDropItems;

public class BetterDropItems extends JavaPlugin {

    @Override
    public void onEnable() {
        
        this.registerEvent(
            new CheckDamageEntity(),
            new HandleDropItems()
        );
    }


    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "{0}.onDisable()", this.getClass().getName());
    }

    protected void registerEvent(Listener ...listeners) {
        
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }
}
