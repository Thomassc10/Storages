package me.thomas.storages.events;

import me.thomas.storages.utils.HiddenStringUtils;
import me.thomas.storages.utils.Storage;
import me.thomas.storages.utils.StoragesManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class SaveItems implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals("Storages")) return;
        StoragesManager storagesManager = StoragesManager.getStoragesManager();
        for (Storage storage : storagesManager.getStorages())
            if (!event.getView().getTitle().startsWith(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', storage.getName())))) return;
        String[] args = event.getView().getTitle().split(" ");
        int slot = Integer.parseInt(HiddenStringUtils.extractHiddenString(args[args.length - 1]));
        storagesManager.saveStorageItems((Player) event.getPlayer(), slot, event.getInventory().getContents());
    }
}
