package me.knifeworm.openlight.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeAdventure implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("OpenLight.adventure"))
        {
            if(args.length == 0)
            {
                Player player = (Player) sender;
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ChatColor.GREEN + "Your gamemode has been changed to adventure.");
            }
            else if(args.length == 1)
            {
                Player target = Bukkit.getPlayer(args[0]);
                target.setGameMode(GameMode.ADVENTURE);
                target.sendMessage(ChatColor.GREEN + "Your gamemode has been changed to adventure.");
                sender.sendMessage(ChatColor.GREEN + target.getName() + "'s gamemode has been changed to adventure.");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "You don't have the right permission to run this command.");
        }
        return false;
    }
}
