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

public class OpenPlayerStoragesInv implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("openstr")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You cannot do this!");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("storages.use.admin")) {
                player.sendMessage(ChatColor.RED + "You are now allowed to use this command!");
                return true;
            }
            StoragesManager storagesManager = StoragesManager.getStoragesManager();
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Usage: '/openstr <player>' or '/openstr <player> <storage-number>'");
                return true;
            }
            if (Bukkit.getPlayer(args[0]) != null) {
                Player p = Bukkit.getPlayer(args[0]);
                storagesManager.getViewingPlayer().put(player.getUniqueId(), p.getUniqueId());
                if (args.length == 1) {
                    Inventory inv = Bukkit.createInventory(null, StoragesMenu.getInventory().getSize(), "Player Storages");
                    inv.setContents(StoragesMenu.getInventory().getContents());
                    player.openInventory(inv);
                    return true;
                }
                int slot = Integer.parseInt(args[1]);
                if (slot > StoragesManager.getStoragesManager().getStorages().size()) {
                    player.sendMessage(ChatColor.RED + "Invalid storage number!");
                    return true;
                }
                Storage storage = storagesManager.getStorages().get(slot - 1);
                Inventory inv = Bukkit.createInventory(null, storage.getSize(), "Player Storage " + HiddenStringUtils.encodeString(String.valueOf(slot)));
                inv.setContents(storagesManager.getStorageItems(p, slot));
                player.openInventory(inv);
                return true;
            }
        }
        return false;
    }
}
