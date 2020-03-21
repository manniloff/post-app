package com.optimal.solution.dto;

import com.optimal.solution.util.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String login;
    private String password;
    private boolean isActive;
    private Roles role;
}
