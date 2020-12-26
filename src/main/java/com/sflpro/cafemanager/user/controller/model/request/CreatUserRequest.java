package com.sflpro.cafemanager.user.controller.model.request;

import com.sflpro.cafemanager.user.domain.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreatUserRequest {
    @NotNull
    private UserRole role;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
