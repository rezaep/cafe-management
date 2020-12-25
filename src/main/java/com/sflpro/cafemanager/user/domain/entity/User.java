package com.sflpro.cafemanager.user.domain.entity;

import com.sflpro.cafemanager.user.domain.enums.UserRole;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private UserRole role;
    private String username;
}