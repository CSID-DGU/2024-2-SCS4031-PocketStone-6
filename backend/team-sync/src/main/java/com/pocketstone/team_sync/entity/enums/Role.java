package com.pocketstone.team_sync.entity.enums;

public enum Role {

    BACKEND_DEVELOPER("BE"),
    FRONTEND_DEVELOPER("FE"),
    PRODUCT_MANAGER("PM"),
    UI_UX_DESIGNER("UI/UX Designer"),
    DATA_ANALYST("DA");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    // label로 Role을 찾는 메서드 추가
    public static Role fromLabel(String label) {
        for (Role role : Role.values()) {
            if (role.getLabel().equalsIgnoreCase(label)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role label: " + label);
    }
}
