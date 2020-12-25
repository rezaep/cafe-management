package com.sflpro.cafemanager.table.controller.model.response;

import com.sflpro.cafemanager.table.domain.model.AssignedTableModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignedTablesResponse {
    private List<AssignedTableModel> items;
}
