package me.knifeworm.openlight.checks.combat;

import me.knifeworm.openlight.checks.CheckResult;
import me.knifeworm.openlight.checks.CheckType;
import me.knifeworm.openlight.util.Distance;
import me.knifeworm.openlight.util.User;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class WallHit {

    private static final CheckResult PASS = new CheckResult(false, CheckType.WALLHIT, "");

    public static CheckResult runCheck(User user, Entity entity) {
        System.out.println("Loaded module: WallHit");
        Distance distance = new Distance(user.getPlayer().getLocation(), entity.getLocation());
        double x = distance.getXDifference();
        double z = distance.getZDifference();
        Player p = user.getPlayer();

        if (x == 0 || z == 0) {
            System.out.println("Warning! Positions are same");
            return PASS;
        }

        if (distance.getYDifference() >= .6)
            return PASS;

        Location l = null;

        if (x <= .5 && z >= 1) {
            if (p.getLocation().getZ() > entity.getLocation().getZ()) {
                l = p.getLocation().clone().add(0, 0, -1);
            } else {
                l = p.getLocation().clone().add(0, 0, 1);
            }
        } else if (z <= .5 && x >= 1) {
            if (p.getLocation().getX() > entity.getLocation().getX()) {
                l = p.getLocation().clone().add(-1, 0, 0);
            } else {
                l = p.getLocation().clone().add(-1, 0, 0);
            }
        }
        boolean failed = false;

        if (l != null)
            failed = l.getBlock().getType().isSolid() && l.clone().add(0, 1, 0).getBlock().getType().isSolid();

        return failed ? new CheckResult(true, CheckType.WALLHIT, "tried to hit"
                + (entity.getType() != EntityType.PLAYER ? " a" : "") + " " + entity.getName() + " through a wall")
                : PASS;
    }
}
