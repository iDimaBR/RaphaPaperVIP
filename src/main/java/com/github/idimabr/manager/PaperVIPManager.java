package com.github.idimabr.manager;

import com.github.idimabr.RaphaPaperVIP;
import com.github.idimabr.model.VIP;
import com.github.idimabr.utils.ConfigUtil;
import com.github.idimabr.utils.ItemBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter @Setter
public class PaperVIPManager {

    private final RaphaPaperVIP plugin;
    private Map<String, VIP> paperMap = new HashMap<>();
    private ItemBuilder modelBuilder;

    public void load(){
        final ConfigUtil config = plugin.getConfig();

        modelBuilder = new ItemBuilder(config.getString("Item.material"))
                .setName(config.getString("Item.name"))
                .setLore(config.getStringList("Item.lore"));

        for (String key : config.getConfigurationSection("VIPS").getKeys(false)) {
            paperMap.put(
                    key.toLowerCase(),
                    new VIP(
                            config.getString("VIPS." + key + ".name"),
                            config.getString("VIPS." + key + ".command")
                    )
            );
            plugin.getLogger().info("VIP '" + key + "' loaded.");
        }
    }

    public VIP getVIP(String name){
        return paperMap.get(name);
    }

}
