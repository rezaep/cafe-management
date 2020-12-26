package com.sflpro.cafemanager.user.model;

import com.sflpro.cafemanager.user.domain.entity.User;
import com.sflpro.cafemanager.user.domain.enums.UserRole;

public class UserTestDataBuilder {
    public static final String MANAGER_USERNAME = "John";
    public static final String WAITER_USERNAME = "Arthur";
    public static final String PASSWORD = "password";

    private long id;
    private UserRole role;
    private String username;
    private String password;

    private UserTestDataBuilder() {

    }

    public static UserTestDataBuilder aUser() {
        return new UserTestDataBuilder();
    }

    public static UserTestDataBuilder aManager() {
        return aUser()
                .withRole(UserRole.ROLE_MANAGER)
                .withUsername(MANAGER_USERNAME)
                .withPassword(PASSWORD);
    }

    public static UserTestDataBuilder aWaiter() {
        return aUser()
                .withRole(UserRole.ROLE_WAITER)
                .withUsername(WAITER_USERNAME)
                .withPassword(PASSWORD);
    }

    public UserTestDataBuilder with(long id, UserRole role, String username, String password) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;

        return this;
    }

    public UserTestDataBuilder withId(long id) {
        this.id = id;

        return this;
    }

    public UserTestDataBuilder withRole(UserRole role) {
        this.role = role;

        return this;
    }

    public UserTestDataBuilder withUsername(String username) {
        this.username = username;

        return this;
    }

    public UserTestDataBuilder withPassword(String password) {
        this.password = password;

        return this;
    }

    public User build() {
        return new User()
                .setId(id)
                .setRole(role)
                .setUsername(username)
                .setPassword(password);
    }
}
