package me.knifeworm.openlight.permission;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.permissions.PermissionAttachment;

public final class PermissionTable {
    private final Map<Permission, PermissionStatus> permissions;

    private PermissionTable(Map<Permission, PermissionStatus> permissions) {
        this.permissions = permissions;
    }

    public static PermissionTable withPermissions(List<Permission> permissions) {
        Preconditions.checkNotNull(permissions);
        var permissionMap = permissions.parallelStream()
                .collect(Collectors.toMap(key -> key, value -> PermissionStatus.ALLOWED));
        return withPermissions(permissionMap);
    }

    public static PermissionTable withBoolPermissions(
            Map<String, Boolean> permissions
    ) {
        var mappedPermissions = permissions.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Permission.of(entry.getKey()),
                        entry -> PermissionStatus.of(entry.getValue())
                ));
        return withPermissions(mappedPermissions);
    }

    public static PermissionTable withPermissions(
            Map<Permission, PermissionStatus> permissions
    ) {
        Preconditions.checkNotNull(permissions);
        return new PermissionTable(Maps.newHashMap(permissions));
    }

    public static PermissionTable empty() {
        return withPermissions(Maps.newHashMap());
    }

    public PermissionStatus statusOf(Permission permission) {
        Preconditions.checkNotNull(permission);
        return permissions.getOrDefault(permission, PermissionStatus.NOT_SET);
    }


    public PermissionTable merge(PermissionTable source) {
        Preconditions.checkNotNull(source);
        var permissionTable = PermissionTable.withPermissions(permissions);
        source.permissions.forEach(permissionTable::setStatus);
        return permissionTable;
    }


    public void setStatus(Permission permission, PermissionStatus status) {
        Preconditions.checkNotNull(permission);
        Preconditions.checkNotNull(status);
        if (status == PermissionStatus.NOT_SET) {
            unsetPermission(permission);
        } else {
            permissions.put(permission, status);
        }
    }

    private void unsetPermission(Permission permission) {
        permissions.remove(permission);
    }

    public void apply(PermissionAttachment permissionAttachment) {
        Preconditions.checkNotNull(permissionAttachment);
        Map<String, Boolean> permissionsMap = tryExtractPermissionsMap(
                permissionAttachment);
        clonePermissions(permissionsMap);
    }

    private void clonePermissions(Map<String, Boolean> permissionsMap) {
        permissionsMap.clear();
        permissions.forEach((permission, status) ->
                permissionsMap.put(permission.name(), status.booleanValue())
        );
    }

    private Map<String, Boolean> tryExtractPermissionsMap(
            PermissionAttachment permissionAttachment
    ) {
        try {
            var permissionsField = permissionAttachment.getClass()
                    .getDeclaredField("permissions");
            permissionsField.setAccessible(true);
            return (Map<String, Boolean>) permissionsField.get(permissionAttachment);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return Maps.newHashMap();
        }
    }

    public boolean isEmpty() {
        return permissions.isEmpty();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("permissions", permissions)
                .toString();
    }

    public Map<String, Boolean> asMap() {
        return permissions.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> entry.getValue().booleanValue()
                ));
    }
}