package com.makotomiyamoto.nt.encampments.crafting.drops;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemDrop {
    private boolean scaledWithFortune;
    private ItemStack itemStack;

    public ItemDrop() {
        this.scaledWithFortune = false;
        this.itemStack = new ItemStack(Material.AIR);
    }

    public boolean isScaledWithFortune() {
        return scaledWithFortune;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setScaledWithFortune(boolean scaledWithFortune) {
        this.scaledWithFortune = scaledWithFortune;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void drop(Location location) {
        location.getWorld().dropItemNaturally(location, itemStack);
    }
}
