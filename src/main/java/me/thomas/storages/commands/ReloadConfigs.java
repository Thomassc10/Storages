package me.thomas.storages.commands;

import me.thomas.storages.Storages;
import me.thomas.storages.inventories.StoragesMenu;
import me.thomas.storages.utils.StoragesManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadConfigs implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("strreload")) {
            if (sender.hasPermission("storages.use.admin")) {
                StoragesManager storagesManager = StoragesManager.getStoragesManager();
                sender.sendMessage(ChatColor.RED + "Reloading the config files...");
                storagesManager.getStorages().clear();
                Storages.getInstance().reloadConfig();
                storagesManager.getDataManager().getCustomStorages().reloadConfig();
                StoragesMenu.register();
                sender.sendMessage(ChatColor.GREEN + "Reload complete!");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "You are not allowed to use this command!");
            return true;
        }
        return false;
    }
}
