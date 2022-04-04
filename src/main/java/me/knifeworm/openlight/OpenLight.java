package me.knifeworm.openlight;

import me.knifeworm.openlight.checks.CheckResult;
import me.knifeworm.openlight.checks.CheckType;
import me.knifeworm.openlight.commands.*;
import me.knifeworm.openlight.util.Settings;
import me.knifeworm.openlight.util.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import me.knifeworm.openlight.events.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
public final class OpenLight extends JavaPlugin {

    public static final HashMap<UUID, User> USERS = new HashMap<>();
    public static final ArrayList<CheckType> DISABLED_CHECKS = new ArrayList<>();
    public static final boolean SIMPLE_LOG = false;

    @Override
    public void onEnable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "OpenLight has loaded!");
        loadEvents();
        loadCommands();

        new Cleaner().runTaskTimerAsynchronously(this, Settings.CLEAN_UP_DELAY, Settings.CLEAN_UP_DELAY);

        for(Player p : Bukkit.getOnlinePlayers())
            OpenLight.USERS.put(p.getUniqueId(), new User(p));
    }

    @Override
    public void onDisable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "OpenLight has been disabled!");
    }

    // Loads all the commands.
    public void loadCommands()
    {
        Bukkit.getPluginCommand("gms").setExecutor(new GamemodeSurvival());
        Bukkit.getPluginCommand("gmc").setExecutor(new GamemodeCreative());
        Bukkit.getPluginCommand("gmsp").setExecutor(new GamemodeSpectator());
        Bukkit.getPluginCommand("gma").setExecutor(new GamemodeAdventure());
        Bukkit.getPluginCommand("fly").setExecutor(new FlyCommand());
    }

    // Loads all the events.
    public void loadEvents()
    {
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuit(), this);
    }


    public static void log(User u, CheckResult result)
    {
        if(DISABLED_CHECKS.contains(result.getType()))
            throw new IllegalStateException("Error! tried to log a disabled check.");
        String message = ChatColor.RED.toString() + ChatColor.BOLD + "OL: " + u.getPlayer().getName() + " tried to use " + result.getType().getName();
        if(!SIMPLE_LOG)
            message += ChatColor.GRAY + ", " + result.getMessage();
        u.getPlayer().sendMessage(message);
        Bukkit.getLogger().info(message);
    }

    public static User getUser(Player player){
        for(User user : USERS.values())
            if(user.getPlayer() == player || user.getPlayer().getUniqueId().equals(player.getUniqueId()))
                return user;
        return null;
    }

    public static boolean shouldCheck(User user, CheckType type){
        return !DISABLED_CHECKS.contains(type);
    }

    public static boolean isSilent(){
        return false;
    }
}
