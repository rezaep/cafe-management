package com.sflpro.cafemanager.user.model;

import com.sflpro.cafemanager.user.domain.entity.User;
import com.sflpro.cafemanager.user.domain.enums.UserRole;

public class UserTestDataBuilder {
    private long id;
    private UserRole role;
    private String username;

    private UserTestDataBuilder() {

    }

    public static UserTestDataBuilder aUser() {
        return new UserTestDataBuilder();
    }

    public static UserTestDataBuilder aManager() {
        return aUser()
                .withId(1)
                .withRole(UserRole.MANAGER)
                .withUsername("john");
    }

    public static UserTestDataBuilder aWaiter() {
        return aUser()
                .withId(1)
                .withRole(UserRole.WAITER)
                .withUsername("arthur");
    }

    public UserTestDataBuilder with(long id, UserRole role, String username) {
        this.id = id;
        this.role = role;
        this.username = username;

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

    public User build() {
        return new User()
                .setId(id)
                .setRole(role)
                .setUsername(username);
    }
}
