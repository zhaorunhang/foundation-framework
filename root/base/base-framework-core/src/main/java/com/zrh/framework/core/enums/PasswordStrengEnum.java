package com.zrh.framework.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PasswordStrengEnum {
    LOWERCASE(".*[a-z].*"),
    UPPERCASE(".*[A-Z].*"),
    NUMBER(".*[0-9].*"),
    SPECIAL(".*[^a-zA-Z0-9].*"),
    ;
    @Getter
    private final String matche;

    public static boolean getPasswordStreng(String password) {
        boolean hasMissing = false;
        int typeCount = 0;
        for (PasswordStrengEnum passwordStreng : PasswordStrengEnum.values()) {
            if (password.matches(passwordStreng.getMatche())) {
                typeCount++;
            }
        }
        if (typeCount >= 3) {
            hasMissing = true;
        }
        return hasMissing;
    }

}
