package com.pocketstone.team_sync.entity.enums;

public enum Role {

    BACKEND_DEVELOPER("Backend Developer"),
    FRONTEND_DEVELOPER("Frontend Developer"),
    PRODUCT_MANAGER("Product Manager"),
    UI_UX_DESIGNER("UI/UX Designer"),
    DATA_ANALYST("Data Analyst");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
