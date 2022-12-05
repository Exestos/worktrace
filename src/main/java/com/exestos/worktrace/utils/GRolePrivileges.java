package com.exestos.worktrace.utils;


public class GRolePrivileges {

    public static int INVITE = 1;
    public static int CREATE_TASK = 2;
    public static int REMOVE_USER = 3;

    private long privileges;

    public GRolePrivileges() {
        this.privileges = 0L;
    }

    public GRolePrivileges(long privileges) {
        this.privileges = privileges;
    }

    public static long creatorPrivileges() {
        return new GRolePrivileges()
                .setPrivilege(INVITE)
                .setPrivilege(CREATE_TASK)
                .setPrivilege(REMOVE_USER)
                .privileges;
    }

    public static long defaultPrivileges() {
        return new GRolePrivileges()
                .privileges;
    }

    public boolean hasPrivilege(int privilege) {
        long mask = 1L << privilege - 1;
        return (this.privileges & mask) != 0;
    }

    public GRolePrivileges setPrivilege(int privilege) {
        return setPrivilege(privilege, true);
    }

    public GRolePrivileges setPrivilege(int privilege, boolean value) {
        long mask = 1L << privilege - 1;
        if (value) {
            this.privileges |= mask;
        } else {
            this.privileges &= ~mask;
        }
        return this;
    }
}