package com.sflpro.cafemanager.table.domain.entity;

import com.sflpro.cafemanager.user.domain.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "table")
@javax.persistence.Table(name = "`table`")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
