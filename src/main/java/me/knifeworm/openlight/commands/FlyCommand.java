package me.knifeworm.openlight.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("OpenLight.fly"))
        {
            if(args.length == 0)
            {
                Player player = (Player) sender;
                if(player.getAllowFlight())
                {
                    player.setFlying(false);
                    player.sendMessage(ChatColor.GREEN + "Your wings have withered away.");
                }
                else
                {
                    player.setFlying(true);
                    player.sendMessage(ChatColor.GREEN + "You have grown wings.");
                }
            }
            else if(args.length == 1)
            {
                Player target = Bukkit.getPlayer(args[0]);
                if(target.getAllowFlight())
                {
                    target.setFlying(false);
                    target.sendMessage(ChatColor.GREEN + "Your wings have withered away.");
                    sender.sendMessage(ChatColor.GREEN + target.getName() + "'s wings have withered away.");
                }
                else
                {
                    target.setFlying(true);
                    target.sendMessage(ChatColor.GREEN + "You have grown wings.");
                    sender.sendMessage(ChatColor.GREEN + target.getName() + "has grown wings.");
                }
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
        }
        return false;
    }
}
