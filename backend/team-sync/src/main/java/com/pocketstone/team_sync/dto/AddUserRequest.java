package com.pocketstone.team_sync.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserRequest {
    private String loginId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
