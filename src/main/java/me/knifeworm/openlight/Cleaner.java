package me.knifeworm.openlight;

import me.knifeworm.openlight.util.User;
import org.bukkit.scheduler.BukkitRunnable;

public class Cleaner extends BukkitRunnable
{
    @Override
    public void run(){
        for(User user : OpenLight.USERS.values()){
            user.getHits();
            user.getEntities();
        }
    }
}
