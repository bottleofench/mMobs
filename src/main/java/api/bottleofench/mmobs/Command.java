package api.bottleofench.mmobs;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Command implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 0) {
            if (args[0].equals("spawn")) {
                if (args.length == 2) {
                    File file = new File(MMobs.getInstance().getDataFolder().getPath() + File.separator + "data" + File.separator + args[1]);
                    Player p = (Player) sender;

                    if (!file.getName().endsWith(".yml")) return false;
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                    if (config.get("type") != null) {
                        LivingEntity entity = (LivingEntity) p.getWorld().spawnEntity(p.getLocation(), EntityType.valueOf(config.getString("type")));
                            MobsUtil.applyParams(entity, config);
                            MobsUtil.applyEffects(entity, config);
                            MobsUtil.applyEquipment(entity, config);
                            MobsUtil.applyAttributes(entity, config);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("spawn");

            List<String> result = new ArrayList<>();
            for (String b : list) {
                if (b.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(b);
            }
            return result;

        }
        if (args.length == 2) {
            List<String> list = new ArrayList<>();

            for (File file : new File(MMobs.getInstance().getDataFolder().getPath() + File.separator + "data").listFiles()) {
                if (file.getName().endsWith(".yml")) {
                    list.add(file.getName());
                }
            }

            List<String> result = new ArrayList<>();
            for (String b : list) {
                if (b.toLowerCase().startsWith(args[1].toLowerCase()))
                    result.add(b);
            }
            return result;

        }
        return null;
    }
}
