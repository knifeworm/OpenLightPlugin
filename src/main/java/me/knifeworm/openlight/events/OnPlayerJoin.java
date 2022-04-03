package me.knifeworm.openlight.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if(player.hasPlayedBefore())
        {
            event.setJoinMessage(ChatColor.GREEN + "Welcome back to the server " + player.getName());
        }
        else
        {
            event.setJoinMessage(ChatColor.GREEN + "Welcome to the server " + player.getName() + " we hope you enjoy your stay.");
        }
    }
}
