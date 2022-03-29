package api.bottleofench.mmobs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MMobs extends JavaPlugin {

    private static JavaPlugin instance;

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new Event(), this);

        new File(getDataFolder().getPath() + File.separator + "data").mkdirs();

        getCommand("mmobs").setExecutor(new Command());
        getCommand("mmobs").setTabCompleter(new Command());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
