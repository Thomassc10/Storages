package me.thomas.storages.datamanager;

import me.thomas.storages.utils.StoragesManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoragesContent {

    public void saveContent() {
        StoragesManager storagesManager = StoragesManager.getStoragesManager();
        DataManager dataManager = DataManager.getDataManager();
        FileConfiguration config = dataManager.getStoragesItems().getConfig();
        for (Map.Entry<String, HashMap<Integer, ItemStack[]>> entry : storagesManager.getStr().entrySet()) {
            for (Map.Entry<Integer, ItemStack[]> entry1 : entry.getValue().entrySet()) {
                config.set("data." + entry.getKey() + "." + entry1.getKey(), entry1.getValue());
            }
        }
        dataManager.getStoragesItems().saveConfig();
    }

    public void loadContent() {
        HashMap<Integer, ItemStack[]> map = new HashMap<>();
        StoragesManager storagesManager = StoragesManager.getStoragesManager();
        DataManager dataManager = DataManager.getDataManager();
        FileConfiguration config = dataManager.getStoragesItems().getConfig();
        ConfigurationSection section = config.getConfigurationSection("data");
        if (section == null) return;
        for (String key : section.getKeys(false)) {
            for (String key1 : section.getConfigurationSection(key).getKeys(false)) {
                ItemStack[] items = ((List<ItemStack>) section.get(key + "." + key1)).toArray(new ItemStack[0]);
                map.put(Integer.valueOf(key1), items);
                storagesManager.getStr().put(key, map);
            }
        }
    }
}
