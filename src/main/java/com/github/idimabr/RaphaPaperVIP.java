package com.github.idimabr;

import com.github.idimabr.commands.PaperCommand;
import com.github.idimabr.listeners.ActivateVIPListener;
import com.github.idimabr.manager.PaperVIPManager;
import com.github.idimabr.utils.ConfigUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class RaphaPaperVIP extends JavaPlugin {

    private ConfigUtil config;
    private PaperVIPManager manager;

    @Override
    public void onLoad(){
        config = new ConfigUtil(this, "config.yml");
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadManagers();
        loadCommands();
        loadListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadListeners(){
        Bukkit.getPluginManager().registerEvents(new ActivateVIPListener(config, manager), this);
    }

    private void loadCommands(){
        getCommand("papervip").setExecutor(new PaperCommand(manager));
    }
    private void loadManagers(){
        manager = new PaperVIPManager(this);
        manager.load();
    }
}
