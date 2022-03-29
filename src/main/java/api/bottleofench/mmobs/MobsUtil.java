package api.bottleofench.mmobs;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.w3c.dom.Attr;

public class MobsUtil {
    public static void applyParams(LivingEntity entity, FileConfiguration config) {
        if (config.get("customName") != null) {
            entity.customName(Component.text(config.getString("customName")));

            if (config.get("customNameVisible") != null) {
                entity.setCustomNameVisible(config.getBoolean("customNameVisible"));
            }
        }
        if (config.get("health") != null) {
            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(config.getInt("health"));
            entity.setHealth(config.getInt("health"));
        }
        if (config.get("visualFire") != null) {
            entity.setVisualFire(config.getBoolean("visualFire"));
        }
        if (config.get("isBaby") != null) {
            if (entity instanceof Ageable) {
                if (config.getBoolean("isBaby")) {
                    ((Ageable) entity).setBaby();
                }
                else {
                    ((Ageable) entity).setAdult();
                }
            }
        }
        if (config.get("canPickupItems") != null) {
            entity.setCanPickupItems(config.getBoolean("canPickupItems"));
        }
        if (config.get("removeWhenFarAway") != null) {
            entity.setRemoveWhenFarAway(config.getBoolean("removeWhenFarAway"));
        }
        if (config.get("silent") != null) {
            entity.setSilent(config.getBoolean("silent"));
        }
    }

    public static void applyEffects(LivingEntity entity, FileConfiguration config) {
        if (config.get("effects") != null) {
            for (String s : config.getConfigurationSection("effects").getKeys(false)) {
                for (PotionEffectType type : PotionEffectType.values()) {
                    if (type.equals(PotionEffectType.getByName(s))) {
                        int level = config.getConfigurationSection("effects").getInt(s);

                        entity.addPotionEffect(type.createEffect(Integer.MAX_VALUE, level));
                    }
                }
            }
        }
    }

    public static void applyAttributes(LivingEntity entity, FileConfiguration config) {
        if (config.get("attributes") != null) {
            for (String str : config.getConfigurationSection("attributes").getKeys(false)) {
                for (Attribute attribute : Attribute.values()) {
                    if (attribute.equals(Attribute.valueOf(str))) {
                        double baseValue = config.getDouble("attributes." + str);
                        entity.getAttribute(attribute).setBaseValue(baseValue);
                    }
                }
            }
        }
    }

    public static void applyEquipment(LivingEntity entity, FileConfiguration config) {
        if (config.get("equipment") != null) {
            for (String s : config.getConfigurationSection("equipment").getKeys(false)) {
                switch (s) {
                    case "ITEM_IN_MAIN_HAND" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) entity.getEquipment().setItemInMainHandDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        entity.getEquipment().setItemInMainHand(item);
                    }
                    case "ITEM_IN_OFF_HAND" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) entity.getEquipment().setItemInOffHandDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        entity.getEquipment().setItemInOffHand(item);
                    }
                    case "HELMET" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) entity.getEquipment().setHelmetDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        entity.getEquipment().setHelmet(item);
                    }
                    case "CHESTPLATE" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) entity.getEquipment().setChestplateDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        entity.getEquipment().setChestplate(item);
                    }
                    case "LEGGINGS" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                            if (section.get("dropChance") != null) entity.getEquipment().setLeggingsDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        entity.getEquipment().setLeggings(item);
                    }
                    case "BOOTS" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) entity.getEquipment().setBootsDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        entity.getEquipment().setBoots(item);
                    }
                }
            }
        }
    }

    public static void applyAttributes(CreatureSpawnEvent event, FileConfiguration config) {
        if (config.get("attributes") != null) {
            for (String str : config.getConfigurationSection("attributes").getKeys(false)) {
                for (Attribute attribute : Attribute.values()) {
                    if (attribute.equals(Attribute.valueOf(str))) {
                        double baseValue = config.getDouble("attributes." + str);
                        event.getEntity().getAttribute(attribute).setBaseValue(baseValue);
                    }
                }
            }
        }
    }

    public static void applyParams(CreatureSpawnEvent event, FileConfiguration config) {
        if (config.get("customName") != null) {
            event.getEntity().customName(Component.text(config.getString("customName")));

            if (config.get("customNameVisible") != null) {
                event.getEntity().setCustomNameVisible(config.getBoolean("customNameVisible"));
            }
        }
        if (config.get("health") != null) {
            event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(config.getInt("health"));
            event.getEntity().setHealth(config.getInt("health"));
        }
        if (config.get("visualFire") != null) {
            event.getEntity().setVisualFire(config.getBoolean("visualFire"));
        }
        if (config.get("isBaby") != null) {
            if (event.getEntity() instanceof Ageable) {
                if (config.getBoolean("isBaby")) {
                    ((Ageable) event.getEntity()).setBaby();
                }
                else {
                    ((Ageable) event.getEntity()).setAdult();
                }
            }
        }
        if (config.get("canPickupItems") != null) {
            event.getEntity().setCanPickupItems(config.getBoolean("canPickupItems"));
        }
        if (config.get("removeWhenFarAway") != null) {
            event.getEntity().setRemoveWhenFarAway(config.getBoolean("removeWhenFarAway"));
        }
        if (config.get("silent") != null) {
            event.getEntity().setSilent(config.getBoolean("silent"));
        }
    }

    public static void applyEffects(CreatureSpawnEvent event, FileConfiguration config) {
        if (config.get("effects") != null) {
            for (String s : config.getConfigurationSection("effects").getKeys(false)) {
                for (PotionEffectType type : PotionEffectType.values()) {
                    if (type.equals(PotionEffectType.getByName(s))) {
                        int level = config.getConfigurationSection("effects").getInt(s);

                        event.getEntity().addPotionEffect(type.createEffect(Integer.MAX_VALUE, level));
                    }
                }
            }
        }
    }

    public static void applyEquipment(CreatureSpawnEvent event, FileConfiguration config) {
        if (config.get("equipment") != null) {
            for (String s : config.getConfigurationSection("equipment").getKeys(false)) {
                switch (s) {
                    case "ITEM_IN_MAIN_HAND" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) event.getEntity().getEquipment().setItemInMainHandDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        event.getEntity().getEquipment().setItemInMainHand(item);
                    }
                    case "ITEM_IN_OFF_HAND" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) event.getEntity().getEquipment().setItemInOffHandDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        event.getEntity().getEquipment().setItemInOffHand(item);
                    }
                    case "HELMET" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) event.getEntity().getEquipment().setHelmetDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        event.getEntity().getEquipment().setHelmet(item);
                    }
                    case "CHESTPLATE" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) event.getEntity().getEquipment().setChestplateDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        event.getEntity().getEquipment().setChestplate(item);
                    }
                    case "LEGGINGS" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) event.getEntity().getEquipment().setLeggingsDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        event.getEntity().getEquipment().setLeggings(item);
                    }
                    case "BOOTS" -> {
                        ConfigurationSection section = config.getConfigurationSection("equipment." + s);
                        if (section.get("type") == null) return;
                        if (Material.matchMaterial(section.getString("type")) == null) return;
                        ItemStack item = new ItemStack(Material.valueOf(section.getString("type")));
                        if (section.get("amount") != null) item.setAmount(section.getInt("amount"));
                        if (section.get("damage") != null) {
                            Damageable damageable = (Damageable) item.getItemMeta();
                            damageable.setDamage(section.getInt("damage"));
                            item.setItemMeta(damageable);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (section.get("unbreakable") != null) meta.setUnbreakable(section.getBoolean("unbreakable"));
                        if (section.get("dropChance") != null) event.getEntity().getEquipment().setBootsDropChance(section.getInt("dropChance") / 100.0f);
                        if (section.get("enchantments") != null) {
                            for (String str : section.getConfigurationSection("enchantments").getKeys(false)) {
                                for (Enchantment e : Enchantment.values()) {
                                    if (e.equals(Enchantment.getByName(str))) {
                                        int level = section.getInt("enchantments." + str);
                                        meta.addEnchant(e, level, false);
                                    }
                                }
                            }
                        }

                        item.setItemMeta(meta);
                        event.getEntity().getEquipment().setBoots(item);
                    }
                }
            }
        }
    }
}
