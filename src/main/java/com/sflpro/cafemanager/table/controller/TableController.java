package com.sflpro.cafemanager.table.controller;

import com.sflpro.cafemanager.table.controller.model.request.CreatTableRequest;
import com.sflpro.cafemanager.table.controller.model.response.AssignedTablesResponse;
import com.sflpro.cafemanager.table.domain.model.AssignedTableModel;
import com.sflpro.cafemanager.table.domain.model.TableModel;
import com.sflpro.cafemanager.table.service.TableService;
import com.sflpro.cafemanager.user.domain.entity.User;
import com.sflpro.cafemanager.user.domain.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("tables")
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;

    @PostMapping
    @Secured(UserRole.Code.MANAGER)
    public TableModel createTable(@Valid @RequestBody CreatTableRequest request) {
        return tableService.createTable(request.getNumber());
    }

    @GetMapping("assigned")
    @Secured(UserRole.Code.WAITER)
    public AssignedTablesResponse getAssignedTables(@AuthenticationPrincipal User user) {
        List<AssignedTableModel> assignedTables = tableService.getAssignedTables(user.getId());

        return new AssignedTablesResponse(assignedTables);
    }
}
