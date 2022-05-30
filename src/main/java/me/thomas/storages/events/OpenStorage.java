package me.thomas.storages.events;

import me.thomas.storages.utils.HiddenStringUtils;
import me.thomas.storages.utils.Storage;
import me.thomas.storages.utils.StoragesManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class OpenStorage implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().startsWith("Storages")) return;
        if (event.getCurrentItem() == null) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        StoragesManager storagesManager = StoragesManager.getStoragesManager();
        Storage storage = storagesManager.getStorageBySlot(event.getSlot());
        if (!player.hasPermission(storage.getPermission())) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this storage!");
            return;
        }
        if (storage.getName().isEmpty() || storage.getName().equals("")|| storage.getName().equals(" ")) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "Unable to open inventory because storage's name is empty!");
            return;
        }
        int slot = (event.getSlot() + 1);
        String name = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', storage.getName()));
        Inventory inv = Bukkit.createInventory(null, storage.getSize(), name + " " + HiddenStringUtils.encodeString(String.valueOf(slot)));

        inv.setContents(storagesManager.getStorageItems(player, slot));
        player.openInventory(inv);
    }
}
