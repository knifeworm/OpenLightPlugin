package me.knifeworm.openlight.checks.combat;


import me.knifeworm.openlight.checks.CheckResult;
import me.knifeworm.openlight.checks.CheckType;
import me.knifeworm.openlight.util.Distance;
import me.knifeworm.openlight.util.Settings;
import me.knifeworm.openlight.util.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;

public class Reach {

    private static final CheckResult PASS = new CheckResult(false, CheckType.REACH, "");

    public static CheckResult runCheck(User user, Entity entity) {
        Distance distance = new Distance(user.getPlayer().getLocation(), entity.getLocation());
        double x = distance.getXDifference();
        double z = distance.getZDifference();

        double max = user.getPlayer().getGameMode() == GameMode.CREATIVE ? Settings.COMBAT_MAX_REACH_CREATIVE : Settings.COMBAT_MAX_REACH_SURVIVAL;

        if (x > max || z > max)
            return new CheckResult(true, CheckType.REACH, (x > z ? z > max ? "both are " : x + " is " : z + " is ") + "greather than " + max);

        return PASS;
    }

}