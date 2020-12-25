package com.sflpro.cafemanager.table.model;

import com.sflpro.cafemanager.table.domain.entity.Table;
import com.sflpro.cafemanager.user.domain.entity.User;
import com.sflpro.cafemanager.user.model.UserTestDataBuilder;

public class TableTestDataBuilder {
    private long id;
    private int number;
    private User user;

    private TableTestDataBuilder() {

    }

    public static TableTestDataBuilder aTable() {
        return new TableTestDataBuilder();
    }

    public static TableTestDataBuilder anUnassignedTable() {
        return aTable()
                .withId(1)
                .withNumber(1);
    }

    public static TableTestDataBuilder anAssignedTable() {
        return anUnassignedTable()
                .withUser(UserTestDataBuilder.aWaiter().build());
    }

    public TableTestDataBuilder withId(long id) {
        this.id = id;

        return this;
    }

    public TableTestDataBuilder withNumber(int number) {
        this.number = number;

        return this;
    }

    public TableTestDataBuilder withUser(User user) {
        this.user = user;

        return this;
    }

    public Table build() {
        return new Table()
                .setId(id)
                .setNumber(number)
                .setUser(user);
    }
}
