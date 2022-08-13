package com.github.idimabr.listeners;

import com.github.idimabr.manager.PaperVIPManager;
import com.github.idimabr.model.VIP;
import com.github.idimabr.utils.ConfigUtil;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class ActivateVIPListener implements Listener {

    private ConfigUtil config;
    private PaperVIPManager manager;

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(!e.getAction().name().contains("RIGHT")) return;
        final ItemStack item = e.getItem();
        final Player player = e.getPlayer();
        if(item == null) return;

        final NBTItem NBT = new NBTItem(item);
        NBT.mergeNBT(item);

        if(!NBT.hasKey("vip")) return;

        final VIP vip = manager.getVIP(NBT.getString("vip"));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), vip.getCommand().replace("%player%", player.getName()));
        titleActivate(player);
        consumeItemInHand(player, item);
    }

    private void consumeItemInHand(Player player, ItemStack original) {
        final int stackAmount = original.getAmount();
        if (stackAmount > 1) original.setAmount(stackAmount - 1);
        else player.setItemInHand(null);
    }

    private void titleActivate(Player player){
        if(config.getBoolean("Titles.enabled"))
            player.sendTitle(
                    config.getString("Titles.title").replace("%player%", player.getName()),
                    config.getString("Titles.subtitle").replace("%player%", player.getName())
            );
    }

}
