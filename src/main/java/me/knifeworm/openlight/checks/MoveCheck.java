package me.knifeworm.openlight.checks;

import me.knifeworm.openlight.util.Distance;
import me.knifeworm.openlight.util.User;

public abstract class MoveCheck extends Check
{
    public MoveCheck(CheckType type){
        super(type);
    }

    public abstract CheckResult runCheck(User user, Distance distance);
}
