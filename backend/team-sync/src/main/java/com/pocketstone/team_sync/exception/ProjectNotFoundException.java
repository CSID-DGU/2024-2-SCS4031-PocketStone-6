package com.pocketstone.team_sync.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(String projectName) {
        super( ";"+projectName + "' 프로젝트를 찾을 수 없습니다");
    }
}
