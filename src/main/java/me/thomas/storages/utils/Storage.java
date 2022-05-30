package me.thomas.storages.utils;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Storage {

    private ItemStack item;
    private String name;
    private List<String> lore;
    private String permission;
    private int size;
    public Storage(ItemStack item, String name, List<String> lore, String permission, int size) {
        this.item = item;
        this.name = name;
        this.lore = lore;
        this.permission = permission;
        this.size = size;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getPermission() {
        return permission;
    }

    public int getSize() {
        return size;
    }
}
