package api.bottleofench.mmobs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.io.File;
import java.util.Random;

public class Event implements Listener {

    @EventHandler
    public void onEvent(CreatureSpawnEvent event) {
        File[] files = new File(MMobs.getInstance().getDataFolder().getPath() + File.separator + "data").listFiles();

        if (files.length == 0) return;
        for (File file : files) {
            if (!file.getName().endsWith(".yml")) continue;

            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            if (config.get("type") != null) {
                if (event.getEntityType().equals(EntityType.valueOf(config.getString("type")))) {
                    if (config.get("spawnInNature") != null) {
                        if (config.getBoolean("spawnInNature")) {
                            if (config.get("spawnInNatureChance") != null) {
                                if (config.getInt("spawnInNatureChance") >= new Random().nextInt(101)) {
                                    MobsUtil.applyParams(event, config);
                                    MobsUtil.applyEffects(event, config);
                                    MobsUtil.applyEquipment(event, config);
                                    MobsUtil.applyAttributes(event, config);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
