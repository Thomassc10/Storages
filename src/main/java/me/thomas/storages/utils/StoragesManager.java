package me.thomas.storages.utils;

import me.thomas.storages.datamanager.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class StoragesManager {

    private static StoragesManager storagesManager;
    private DataManager dataManager;
    private Map<String, HashMap<Integer, ItemStack[]>> str;
    private Map<UUID, UUID> viewingPlayer;
    private List<Storage> storages;
    public StoragesManager() {
        storagesManager = this;
        dataManager = new DataManager();
        str = new HashMap<>();
        viewingPlayer = new HashMap<>();
        storages = new ArrayList<>();
    }

    public static StoragesManager getStoragesManager() {
        return storagesManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public Map<String, HashMap<Integer, ItemStack[]>> getStr() {
        return str;
    }

    public Map<UUID, UUID> getViewingPlayer() {
        return viewingPlayer;
    }

    public List<Storage> getStorages() {
        return storages;
    }

    public ItemStack[] getStorageItems(Player player, int slot) {
        if (str.containsKey(player.getUniqueId().toString()))
            if (str.get(player.getUniqueId().toString()).containsKey(slot))
                return str.get(player.getUniqueId().toString()).get(slot);
        return new ItemStack[]{};
    }

    public void saveStorageItems(Player player, int slot, ItemStack[] items) {
        if (str.containsKey(player.getUniqueId().toString())) {
            str.get(player.getUniqueId().toString()).put(slot, items);
        } else {
            HashMap<Integer, ItemStack[]> map = new HashMap<>();
            map.put(slot, items);
            str.put(player.getUniqueId().toString(), map);
        }
        /*HashMap<Integer, ItemStack[]> map = new HashMap<>();
        if (!str.containsKey(player.getUniqueId().toString())) {
            map.put(slot, items);
            str.put(player.getUniqueId().toString(), map);
        } else str.get(player.getUniqueId().toString()).put(slot, items);*/
    }

    public void addStorage(Storage storage) {
        storages.add(storage);
    }

    public Storage getStorageByItem(ItemStack item) {
        for (Storage storage : storages) {
            if (storage.getItem().isSimilar(item))
                return storage;
        }
        return null;
    }

    public Storage getStorageBySlot(int slot) {
        return storages.get(slot);
    }
}
