package me.knifeworm.openlight.events;

import me.knifeworm.openlight.OpenLight;
import me.knifeworm.openlight.checks.CheckResult;
import me.knifeworm.openlight.checks.CheckType;
import me.knifeworm.openlight.checks.combat.Reach;
import me.knifeworm.openlight.util.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener
{
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            User user = OpenLight.getUser(player);

            if(OpenLight.shouldCheck(user, CheckType.REACH)){
                CheckResult reach = Reach.runCheck(user, e.getEntity());
                if(reach.failed()){
                    e.setCancelled(true);
                    OpenLight.log(user, reach);
                    return;
                }
            }
        }
    }
}
