package me.knifeworm.openlight;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import me.knifeworm.openlight.events.*;

public final class OpenLight extends JavaPlugin {

    @Override
    public void onEnable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "OpenLight has loaded!");
        loadEvents();
        loadCommands();
    }

    @Override
    public void onDisable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "OpenLight has been disabled!");
    }

    // Loads all the commands.
    public void loadCommands()
    {

    }

    // Loads all the events.
    public void loadEvents()
    {
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuit(), this);
    }
}
