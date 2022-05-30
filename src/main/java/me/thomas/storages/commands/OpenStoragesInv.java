package me.thomas.storages.commands;

import me.thomas.storages.inventories.StoragesMenu;
import me.thomas.storages.utils.HiddenStringUtils;
import me.thomas.storages.utils.Storage;
import me.thomas.storages.utils.StoragesManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OpenStoragesInv implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("storages") || label.equalsIgnoreCase("str")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You cannot do this!");
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                StoragesMenu storagesMenu = new StoragesMenu();
                storagesMenu.openInventory(player);
                return true;
            }
            if (args.length == 1) {
                int slot = Integer.parseInt(args[0]);
                StoragesManager storagesManager = StoragesManager.getStoragesManager();
                if (slot > StoragesManager.getStoragesManager().getStorages().size()) {
                    player.sendMessage(ChatColor.RED + "Invalid storage number!");
                    return true;
                }
                Storage storage = storagesManager.getStorages().get(slot - 1);
                if (!player.hasPermission(storage.getPermission())) {
                    player.sendMessage(ChatColor.RED + "You are not allowed to use this command!");
                    return true;
                }
                String name = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', storage.getName()));
                Inventory inv = Bukkit.createInventory(null, storage.getSize(), name + " " + HiddenStringUtils.encodeString(String.valueOf(slot)));
                inv.setContents(storagesManager.getStorageItems(player, slot));
                player.openInventory(inv);
                return true;
            }
        }
        return false;
    }
}
