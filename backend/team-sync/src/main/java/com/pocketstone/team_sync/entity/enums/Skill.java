package com.pocketstone.team_sync.entity.enums;

public enum Skill {

    // 백엔드 기술
    SPRING_BOOT("Spring Boot"),
    SPRING_FRAMEWORK("Spring Framework"),
    JPA("Java Persistence API (JPA)"),
    REST_API("RESTful API"),
    SPRING_SECURITY("Spring Security"),
    HIBERNATE("Hibernate"),
    MYSQL("MySQL"),
    POSTGRESQL("PostgreSQL"),
    DOCKER("Docker"),
    KUBERNETES("Kubernetes"),
    AWS("Amazon Web Services"),
    DEVOPS("DevOps"),

    // 프론트엔드 기술
    REACT("React"),
    ANGULAR("Angular"),
    VUE("Vue.js"),
    JAVASCRIPT("JavaScript"),
    HTML_CSS("HTML/CSS"),
    TYPESCRIPT("TypeScript"),
    SASS("Sass"),
    GRAPHQL("GraphQL"),

    //데이터 기술이나 pm기술도 추가하기
    PYTHON("Python");




    private final String label;

     // 생성자 정의
    Skill(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
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

}
