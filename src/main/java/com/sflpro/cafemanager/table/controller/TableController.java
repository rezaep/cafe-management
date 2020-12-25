package com.sflpro.cafemanager.table.controller;

import com.sflpro.cafemanager.table.controller.model.request.CreatTableRequest;
import com.sflpro.cafemanager.table.controller.model.response.AssignedTablesResponse;
import com.sflpro.cafemanager.table.domain.model.AssignedTableModel;
import com.sflpro.cafemanager.table.domain.model.TableModel;
import com.sflpro.cafemanager.table.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("tables")
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;

    @PostMapping
    public TableModel createTable(@Valid @RequestBody CreatTableRequest request) {
        return tableService.createTable(request.getNumber());
    }

    @GetMapping("assigned/{userId}") // TODO configure Spring Security and extract userId from token
    public AssignedTablesResponse getAssignedTables(@PathVariable long userId) {
        List<AssignedTableModel> assignedTables = tableService.getAssignedTables(userId);

        return new AssignedTablesResponse(assignedTables);
    }
}
