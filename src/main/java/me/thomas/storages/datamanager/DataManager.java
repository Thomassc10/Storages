package me.thomas.storages.datamanager;

import me.thomas.storages.Storages;

public class DataManager {

    private static DataManager dataManager;
    private ConfigFile customStorages;
    private ConfigFile storagesItems;
    public DataManager() {
        Storages storages = Storages.getInstance();
        this.dataManager = this;
        customStorages = new ConfigFile(storages, "custom-storages.yml");
        storagesItems = new ConfigFile(storages, "storages-items.yml");

        customStorages.saveDefaultConfig();
        storagesItems.saveDefaultConfig();
    }

    public static DataManager getDataManager() {
        return dataManager;
    }

    public ConfigFile getCustomStorages() {
        return customStorages;
    }

    public ConfigFile getStoragesItems() {
        return storagesItems;
    }
}
