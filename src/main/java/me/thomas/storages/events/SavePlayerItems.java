package me.thomas.storages.events;

import me.thomas.storages.utils.HiddenStringUtils;
import me.thomas.storages.utils.StoragesManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class SavePlayerItems implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().startsWith("Player Storages")) return;
        if (!event.getView().getTitle().startsWith("Player Storage")) return;
        StoragesManager storagesManager = StoragesManager.getStoragesManager();
        String[] args = event.getView().getTitle().split(" ");
        int slot = Integer.parseInt(HiddenStringUtils.extractHiddenString(args[args.length - 1]));
        Player player = Bukkit.getPlayer((storagesManager.getViewingPlayer().get(event.getPlayer().getUniqueId())));
        storagesManager.saveStorageItems(player, slot, event.getInventory().getContents());
    }
}
