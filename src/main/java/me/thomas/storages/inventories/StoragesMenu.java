package me.thomas.storages.inventories;

import me.thomas.storages.Storages;
import me.thomas.storages.datamanager.DataManager;
import me.thomas.storages.utils.Storage;
import me.thomas.storages.utils.StoragesManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class StoragesMenu {

    private static Inventory INV;

    public static void register() {
        Storages storages = Storages.getInstance();
        FileConfiguration config = storages.getConfig();
        StoragesManager storagesManager = StoragesManager.getStoragesManager();
        Bukkit.getLogger().info("[Storages] Loading storages...");
        try {
            int size = Integer.parseInt(config.getString("menu-size"));
            int size1 = Integer.parseInt(config.getString("storage.size"));

            if (size % 9 != 0 || size1 % 9 != 0) {
                Bukkit.getLogger().warning("[Storages] There is an invalid value for the size of the inventory!");
                Bukkit.getLogger().info("[Storages] Disabling the plugin due to an error.");
                Bukkit.getPluginManager().disablePlugin(storages);
                return;
            }

            Inventory inv = Bukkit.createInventory(null, size, "Storages");

            if (config.getString("default-storages").contains("true")) {
                ItemStack item = new ItemStack(Material.matchMaterial(config.getString("storage.item").toUpperCase()));
                ItemMeta meta = item.getItemMeta();

                String name = config.getString("storage.name");
                for (int i = 0; i < Integer.parseInt(config.getString("storages-amount")); i++) {
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
                    List<String> lore = new ArrayList<>();
                    for (String s : config.getStringList("storage.lore"))
                        lore.add(ChatColor.translateAlternateColorCodes('&', s));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    inv.setItem(i, item);
                    Storage storage = new Storage(item, name, lore, "storages.use.str" + (i + 1), size1);
                    storagesManager.addStorage(storage);
                }
            } else if (config.getString("default-storages").contains("false")) {
                DataManager dataManager = DataManager.getDataManager();
                FileConfiguration config1 = dataManager.getCustomStorages().getConfig();
                ConfigurationSection section = config1.getConfigurationSection("storages");

                if (section == null) {
                    Bukkit.getLogger().info("[Storages] Unable to find section 'storages' in custom-storages.yml!");
                    Bukkit.getLogger().info("[Storages] Create section or change 'default-storages' to 'true' in the config.yml!");
                    Bukkit.getLogger().info("[Storages] Disabling the plugin due to an error.");
                    Bukkit.getPluginManager().disablePlugin(storages);
                    return;
                }

                List<ItemStack> items = new ArrayList<>();
                for (String key : section.getKeys(false)) {
                    ItemStack item = new ItemStack(Material.matchMaterial(section.getString(key + ".item").toUpperCase()));
                    ItemMeta meta = item.getItemMeta();
                    String name = section.getString(key + ".name");
                    int size2 = Integer.parseInt(section.getString(key + ".size"));
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
                    List<String> lore = new ArrayList<>();
                    for (String s : section.getStringList(key + ".lore"))
                        lore.add(ChatColor.translateAlternateColorCodes('&', s));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    items.add(item);
                    Storage storage = new Storage(item, name, lore, "storages.use." + key, size2);
                    storagesManager.addStorage(storage);
                }

                for (int i = 0; i < items.size(); i++) {
                    inv.setItem(i, items.get(i));
                }
            }
            Bukkit.getLogger().info("[Storages] Successfully loaded all storages!");
            setInventory(inv);
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("[Storages] Invalid value in the config file(s)!");
            Bukkit.getLogger().info("[Storages] Disabling the plugin due to an syntax error.");
            Bukkit.getPluginManager().disablePlugin(storages);
        }
    }

    public static Inventory getInventory() {
        return INV;
    }

    public static void setInventory(Inventory inv) {
        StoragesMenu.INV = inv;
    }

    public void openInventory(Player player) {
        player.openInventory(INV);
    }
}