package me.thomas.storages;

import me.thomas.storages.commands.OpenPlayerStoragesInv;
import me.thomas.storages.commands.OpenStoragesInv;
import me.thomas.storages.commands.ReloadConfigs;
import me.thomas.storages.datamanager.DataManager;
import me.thomas.storages.datamanager.StoragesContent;
import me.thomas.storages.events.OpenPlayerStorage;
import me.thomas.storages.events.OpenStorage;
import me.thomas.storages.events.SaveItems;
import me.thomas.storages.events.SavePlayerItems;
import me.thomas.storages.inventories.StoragesMenu;
import me.thomas.storages.sql.MySQL;
import me.thomas.storages.sql.SQLGetter;
import me.thomas.storages.utils.StoragesManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Storages extends JavaPlugin {

    private static Storages instance;
    private StoragesManager storagesManager;
    private StoragesContent storagesContent;
    public MySQL sql;
    public SQLGetter data;

    @Override
    public void onEnable() {
        instance = this;
        storagesManager = new StoragesManager();
        storagesContent = new StoragesContent();
        sql = new MySQL();
        data = new SQLGetter(this);
        DataManager dataManager = storagesManager.getDataManager();
        registerCommands();
        registerEvents();
        this.saveDefaultConfig();
        StoragesMenu.register();

        int pluginId = 13868;
        new Metrics(this, pluginId);

        if (dataManager.getStoragesItems().getConfig().contains("data"))
            storagesContent.loadContent();
            dataManager.getStoragesItems().getConfig().set("data", null);
            dataManager.getStoragesItems().saveConfig();
    }

    @Override
    public void onDisable() {
        if (!storagesManager.getStr().isEmpty())
            storagesContent.saveContent();
    }

    public static Storages getInstance() {
        return instance;
    }

    public void registerEvents() {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new OpenStorage(), this);
        manager.registerEvents(new SaveItems(), this);
        manager.registerEvents(new OpenPlayerStorage(), this);
        manager.registerEvents(new SavePlayerItems(), this);
    }

    public void registerCommands() {
        this.getCommand("storages").setExecutor(new OpenStoragesInv());
        this.getCommand("openstr").setExecutor(new OpenPlayerStoragesInv());
        this.getCommand("strreload").setExecutor(new ReloadConfigs());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("debug")) {
            storagesManager.getStr().forEach((s, integerHashMap) -> {
                System.out.println("UUID: " + s + "\nRest: " + integerHashMap);
                //System.out.println(integerHashMap);
            });
            return true;
        }
        return false;
    }
}
