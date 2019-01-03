package ua.com.pollapp.util;

import ua.com.pollapp.model.AbstractBaseEntity;

public class SecurityUtil {

    private static int id = AbstractBaseEntity.START_SEQ;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id + 1;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }
}
