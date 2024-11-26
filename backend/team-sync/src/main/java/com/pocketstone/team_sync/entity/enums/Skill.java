package com.pocketstone.team_sync.entity.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Skill {

    // 백엔드 기술
    SPRING_BOOT("SpringBoot", Role.BACKEND_DEVELOPER),
    SPRING_FRAMEWORK("Spring Framework", Role.BACKEND_DEVELOPER),
    //JPA("Java Persistence API (JPA)", Role.BACKEND_DEVELOPER),
    //REST_API("RESTful API", Role.BACKEND_DEVELOPER),
    //SPRING_SECURITY("Spring Security", Role.BACKEND_DEVELOPER),
    //HIBERNATE("Hibernate", Role.BACKEND_DEVELOPER),
    //MYSQL("MySQL", Role.BACKEND_DEVELOPER),
    //POSTGRESQL("PostgreSQL", Role.BACKEND_DEVELOPER),
    //DOCKER("Docker", Role.BACKEND_DEVELOPER),
    //KUBERNETES("Kubernetes", Role.BACKEND_DEVELOPER),
    //AWS("Amazon Web Services", Role.BACKEND_DEVELOPER),
    //DEVOPS("DevOps", Role.BACKEND_DEVELOPER),
    RUBY("Ruby", Role.BACKEND_DEVELOPER),
    NODE_JS("Node.js", Role.BACKEND_DEVELOPER),
    DJANGO("Django",Role.BACKEND_DEVELOPER),
    FAST_API("Fast API",Role.BACKEND_DEVELOPER),
    FLASK("Flask",Role.BACKEND_DEVELOPER),


    // 프론트엔드 기술
    REACT("React", Role.FRONTEND_DEVELOPER),
    ANGULAR("Angular", Role.FRONTEND_DEVELOPER),
    VUE("Vue", Role.FRONTEND_DEVELOPER),
    JAVASCRIPT("JavaScript", Role.FRONTEND_DEVELOPER),
    HTML_CSS("HTML/CSS", Role.FRONTEND_DEVELOPER),
    TYPESCRIPT("TypeScript", Role.FRONTEND_DEVELOPER),
    SASS("Sass", Role.FRONTEND_DEVELOPER),
    GRAPHQL("GraphQL", Role.FRONTEND_DEVELOPER),
    J_QUERY("jQuery", Role.FRONTEND_DEVELOPER),

    // 데이터 분석 기술
    PYTHON("python", Role.DATA_ANALYST),
    R("R", Role.DATA_ANALYST),
    SAS("SAS", Role.DATA_ANALYST),

    // 제품 관리 기술
    AGILE("Agile Methodology", Role.PRODUCT_MANAGER),
    SCRUM("Scrum", Role.PRODUCT_MANAGER),
    ROADMAP("Product Roadmap Planning", Role.PRODUCT_MANAGER),
    USER_STORIES("User Stories", Role.PRODUCT_MANAGER),
    JIRA("JIRA", Role.PRODUCT_MANAGER),
    TREND_ANALYSIS("Market Trend Analysis", Role.PRODUCT_MANAGER),
    SWOT_ANALYSIS("SWOT Analysis", Role.PRODUCT_MANAGER),

    // UI/UX 디자인 기술
    //WIREFRAMING("Wireframing", Role.UI_UX_DESIGNER),
    //PROTOTYPING("Prototyping", Role.UI_UX_DESIGNER),
    ADOBE_XD("Adobe XD", Role.UI_UX_DESIGNER),
    FIGMA("Figma", Role.UI_UX_DESIGNER),
    //USER_RESEARCH("User Research", Role.UI_UX_DESIGNER),
    //A_B_TESTING("A/B Testing", Role.UI_UX_DESIGNER),
    //UX_WRITING("UX Writing", Role.UI_UX_DESIGNER),
    IN_VISION("InVision", Role.UI_UX_DESIGNER),
    SKETCH("Sketch", Role.UI_UX_DESIGNER);



    private final String label;
    private final Role role;
    
     // 생성자 정의
    Skill(String label, Role role) {
        this.label = label;
        this.role = role;
    }
    public String getLabel() {
        return label;
    }

    public Role getRole() {
        return role;
    }

    // label로 Skill을 찾는 메서드 추가
    public static Skill fromLabel(String label) {
        for (Skill skill : Skill.values()) {
            if (skill.getLabel().equalsIgnoreCase(label.trim())) {
                return skill;
            }
        }
        throw new IllegalArgumentException("Invalid skill label: " + label);
    }

     // 역할 라벨별로 스킬 목록을 반환하는 메서드
    public static Map<String, List<String>> getSkills() {
        Map<String, List<String>> skillsByRoleLabel = new HashMap<>();
        for (Skill skill : Skill.values()) {
            String roleLabel = skill.getRole().getLabel();
            skillsByRoleLabel
                .computeIfAbsent(roleLabel, k -> new ArrayList<>())
                .add(skill.getLabel());
        }
        return skillsByRoleLabel;
    }

}
