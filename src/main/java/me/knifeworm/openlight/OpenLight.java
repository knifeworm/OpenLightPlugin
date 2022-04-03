package me.knifeworm.openlight;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpenLight extends JavaPlugin {

    @Override
    public void onEnable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "OpenLight has loaded!");
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

    }
}
