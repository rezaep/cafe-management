package com.sflpro.cafemanager.user.domain.model;

import com.sflpro.cafemanager.user.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private long id;
    private UserRole role;
    private String username;
}
