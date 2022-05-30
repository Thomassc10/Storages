package me.thomas.storages.sql;

import me.thomas.storages.Storages;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {

    private Storages plugin;
    public SQLGetter(Storages plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS storages " +
                    "(NAME VARCHAR(100),UUID VARCHAR(100),ITEMS OBJECT(100),PRIMARY KEY (NAME))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!exists(uuid)) {
                PreparedStatement ps = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO storages " +
                        "(NAME,UUID) VALUES (?,?)");
                ps.setString(1, player.getName());
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM storages WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addItems(UUID uuid, ItemStack[] items) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE storages SET ITEMS=? WHERE UUID=?");
            ps.setObject(1, items);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ItemStack[] getItems(UUID uuid) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT ITEMS FROM storages WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            ItemStack[] items;
            if (rs.next()) {
                items = (ItemStack[]) rs.getObject("ITEMS");
                return items;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ItemStack[]{};
    }
}
