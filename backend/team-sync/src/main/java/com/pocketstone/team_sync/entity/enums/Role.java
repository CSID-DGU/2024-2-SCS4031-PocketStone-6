package com.pocketstone.team_sync.entity.enums;

public enum Role {

    BACKEND_DEVELOPER("Backend Developer"),
    FRONTEND_DEVELOPER("Frontend Developer"),
    PRODUCT_MANAGER("Product Manager"),
    //QA_ENGINEER("QA Engineer"),
    //DEVOPS_ENGINEER("DevOps Engineer"),
    UI_UX_DESIGNER("UI/UX Designer");
    //BUSINESS_ANALYST("Business Analyst")

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
