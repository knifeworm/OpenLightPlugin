package me.knifeworm.openlight.checks.movement;

import me.knifeworm.openlight.checks.CancelType;
import me.knifeworm.openlight.checks.CheckResult;
import me.knifeworm.openlight.checks.CheckType;
import me.knifeworm.openlight.checks.MoveCheck;
import me.knifeworm.openlight.util.*;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NormalMovements extends MoveCheck
{
    public static final CheckResult PASS = new CheckResult(false, CheckType.NORMALMOVEMENTS, "");

    public NormalMovements(){
        super(CheckType.NORMALMOVEMENTS);
        cancelType = CancelType.NOTHING;
    }

    public CheckResult runCheck(User user, Distance distance){
        if(!distance.isGoingUp() || distance.getYDifference() == 0){
            if(user.wasGoingUp && user.oldTicksUp != YMap.get(user.oldYModifier).size())
                return new CheckResult(true, CheckType.NORMALMOVEMENTS, "reason: early, ticks: " + user.oldTicksUp + ", exp: " + YMap.get(user.oldYModifier).size());
            user.wasGoingUp = false;
            user.oldYModifier = 0;
            user.ticksUp = 0;
            return PASS;
        }
        user.wasGoingUp = true;
        int ticksUp = user.ticksUp;
        user.ticksUp++;
        user.oldTicksUp = ticksUp;

        final double speed = Settings.round(distance.getYDifference());

        int id = getYModifer(user);
        if(id > user.oldYModifier)
            user.oldYModifier = id;
        id = user.oldYModifier;
        YMap map = YMap.get(id);

        if(distance.isGoingUp() && distance.isMovingHorizontally()){
            boolean step = MovementUtil.isStepping(distance.getFrom()) || MovementUtil.isStepping(distance.getTo());
            boolean yMap = map != null && map.contains(speed);
            debug(yMap);
            if(step){
                if(speed > .5)
                    return new CheckResult(true, CheckType.NORMALMOVEMENTS, "reason: step, type: " + (speed > .5 ? "high": "low") + ", y: " + speed);
                return PASS;
            }
        }

        if(map == null){
            Bukkit.getLogger().warning("Modifier '" + id + "' has no contents!");
            return PASS;
        }
        if(!map.hasSpeed(ticksUp)){
            return new CheckResult(true, CheckType.NORMALMOVEMENTS, "reason: long, s: " + ticksUp + ", m: " + map.size());
        }

        debug("(" + id + ") " + ticksUp + "=" + speed);

        if(map.size() <= ticksUp)
            if(!(id != 0 && distance.isMovingHorizontally() && map.size() == ticksUp && speed == map.getSpeed(ticksUp)))
                new CheckResult(true, CheckType.NORMALMOVEMENTS, "reason: to high (ticksUp: " + ticksUp + ", max: " + (map.size()-1));

        if(map.size() < ticksUp)
            return PASS;
        double expected = map.getSpeed(ticksUp);

        if(expected != speed){
            debug(ticksUp);
            return new CheckResult(true, CheckType.NORMALMOVEMENTS, "reason: normal, type: " + (expected < speed ? "high" : "low") + " (speed: " + speed + ", expected: " + expected);
        }

        return PASS;
    }

    public int getYModifer(User user){
        if(user.getPlayer().hasPotionEffect(PotionEffectType.JUMP))
            for(PotionEffect pe : user.getPlayer().getActivePotionEffects()) {
                debug(pe.getAmplifier() + " // " + pe.getType());
                if (pe.getType().equals(PotionEffectType.JUMP))
                    return pe.getAmplifier() + 1;

            }
        return 0;
    }
}
