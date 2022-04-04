package me.knifeworm.openlight.events;

import me.knifeworm.openlight.OpenLight;
import me.knifeworm.openlight.checks.CheckResult;
import me.knifeworm.openlight.checks.MoveCheck;
import me.knifeworm.openlight.checks.combat.Reach;
import me.knifeworm.openlight.util.Distance;
import me.knifeworm.openlight.util.User;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class CheckManager implements Listener
{
    private ArrayList<MoveCheck> moveChecks;

    public CheckManager(){
        moveChecks = new ArrayList<>();
    }

    private void addCheck(MoveCheck moveCheck){
        moveChecks.add(moveCheck);
        Bukkit.getLogger().info("/t" + moveCheck.getType().getName() + " has been enabled.");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        User user = OpenLight.getUser(e.getPlayer());
        Distance distance = new Distance(e);
        for(MoveCheck check : moveChecks)
            if(OpenLight.shouldCheck(user, check.getType())){
                CheckResult result = check.runCheck(user, distance);
                if(result.failed()){
                    OpenLight.log(user, result);
                    switch(check.getCancelType()){
                        case EVENT:
                            e.setTo(e.getFrom());
                            break;
                        case PULLDOWN:
                            break;
                        default:
                            break;
                    }
                }
            }
    }
}
