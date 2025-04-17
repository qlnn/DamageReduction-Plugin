package pl.qlnus;

import org.bukkit.plugin.java.JavaPlugin;
import pl.qlnus.listener.EntityDamageByEntityListener;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
       this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);

    }

    @Override
    public void onDisable() {
    }
}
