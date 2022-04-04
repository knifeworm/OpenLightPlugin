package me.knifeworm.openlight.checks.combat;

import me.knifeworm.openlight.checks.CheckResult;
import me.knifeworm.openlight.checks.CheckType;
import me.knifeworm.openlight.util.Settings;
import me.knifeworm.openlight.util.User;
import org.bukkit.entity.Entity;

public class HitSpeed
{
    private static final CheckResult PASS = new CheckResult(false, CheckType.HITSPEED, "");

    public static CheckResult runCheck(User user, Entity entity){
        user.addHit();
        int hits = user.getHits() * 2;

        user.getPlayer().sendMessage("Hots: " + hits);
        if(hits > Settings.COMBAT_MAX_CPS)
            return new CheckResult(true, CheckType.HITSPEED, "tried to hit" + user.getHits() + " times per second!");
        return PASS;
    }
}
