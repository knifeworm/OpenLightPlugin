package me.knifeworm.openlight.checks.combat;

import me.knifeworm.openlight.checks.CheckResult;
import me.knifeworm.openlight.checks.CheckType;
import me.knifeworm.openlight.util.Settings;
import me.knifeworm.openlight.util.User;
import org.bukkit.entity.Entity;

public class MultiAura {
    private static final CheckResult PASS = new CheckResult(false, CheckType.MULTIAURA, "");

    public static CheckResult runCheck(User user, Entity entity){
        user.addEntity(entity.getEntityId());
        int entities = user.getEntities();
        return entities > Settings.MAX_ENTITIES ? new CheckResult(true, CheckType.MULTIAURA, "tried to hit: " + entities + " different entities max(" + Settings.MAX_ENTITIES + ")") : PASS;
    }
}
