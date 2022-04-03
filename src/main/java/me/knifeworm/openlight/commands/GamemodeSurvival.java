package me.knifeworm.openlight.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeSurvival implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("OpenLight.survival"))
        {
            if(args.length == 0)
            {
                Player player = (Player) sender;
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.GREEN + "Your gamemode has been changed to survival!");
            }
            else if(args.length == 1)
            {
                Player target = Bukkit.getPlayer(args[0]);
                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(ChatColor.GREEN + "Your gamemode has been changed to survival!");
                sender.sendMessage(ChatColor.GREEN + target.getName() + "'s gamemode has been changed to survival.");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
        }
        return false;
    }
}
