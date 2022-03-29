package api.bottleofench.mmobs;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.io.File;

public class MMobsAPI {

    public static void spawnMob(Location location, File file) {
        if (!file.getName().endsWith(".yml")) return;
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config.get("type") != null) {
            LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.valueOf(config.getString("type")));
            MobsUtil.applyParams(entity, config);
            MobsUtil.applyEffects(entity, config);
            MobsUtil.applyEquipment(entity, config);
            MobsUtil.applyAttributes(entity, config);
        }
    }

}
