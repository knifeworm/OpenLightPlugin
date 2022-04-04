package me.knifeworm.openlight.checks.movement;

import me.knifeworm.openlight.checks.CheckResult;
import me.knifeworm.openlight.checks.CheckType;
import me.knifeworm.openlight.checks.MoveCheck;
import me.knifeworm.openlight.util.Distance;
import me.knifeworm.openlight.util.MovementUtil;
import me.knifeworm.openlight.util.User;

public class Glide extends MoveCheck
{
    public static final CheckResult PASS = new CheckResult(false, CheckType.GLIDE, "");

    public Glide(){
        super(CheckType.GLIDE);
    }

    public CheckResult runCheck(User user, Distance distance) {
        final double oldY = user.oldY;
//		user.wasGoingUp = distance.getFrom().getY() > distance.getTo().getY();
        user.oldY = distance.getYDifference();
        if (distance.getFrom().getY() > distance.getTo().getY()) {
            if (oldY >= distance.getYDifference() && oldY != 0 && !MovementUtil.shouldNotFlag(distance.getTo())) {
                return new CheckResult(true, CheckType.GLIDE, "tried to glide; " + oldY + " <= " + user.oldY);
            }
        } else {
            user.oldY = 0;
        }
        return PASS;
    }
}
