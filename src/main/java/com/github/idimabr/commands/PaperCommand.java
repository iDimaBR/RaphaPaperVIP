package com.github.idimabr.commands;

import com.github.idimabr.manager.PaperVIPManager;
import com.github.idimabr.model.VIP;
import com.github.idimabr.utils.ItemBuilder;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class PaperCommand implements CommandExecutor {

    private PaperVIPManager manager;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!sender.hasPermission("raphapapervip.admin")){
            sender.sendMessage("§cSem permissão!");
            return false;
        }

        if(args.length < 4){
            sender.sendMessage("§cUtilize /papervip give <player> <vip> <amount>");
            return false;
        }

        if(args[0].equalsIgnoreCase("give")){

            final Player target = Bukkit.getPlayer(args[1]);
            if(target == null){
                sender.sendMessage("§cPlayer não está online.");
                return false;
            }

            final String keyVIP = args[2].toLowerCase();
            final VIP vip = manager.getVIP(keyVIP);
            if(vip == null){
                sender.sendMessage("§cVIP não encontrado!");
                sender.sendMessage("§cVIPS disponíveis: §f" + StringUtils.join(manager.getPaperMap().keySet(), ","));
                return false;
            }

            final ItemBuilder modelBuilder = manager.getModelBuilder().clone();
            modelBuilder.modifyName("%vip%", vip.getName().replace("&","§"));
            modelBuilder.replaceLore("%vip%", vip.getName().replace("&","§"));
            modelBuilder.addNBT("vip", keyVIP);

            int amount = Integer.parseInt(args[3]);
            for(int i = amount;i > 0;i--)
                target.getInventory().addItem(modelBuilder.build());

            sender.sendMessage("§aVocê enviou " + amount + " papéis do VIP '" + keyVIP + "' para o jogador §f" + target.getName());
        }
        return false;
    }
}
