package com.sflpro.cafemanager.table.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableModel {
    private long id;
    private int number;
    private Long userId;
}
