package com.sflpro.cafemanager.table.controller;

import com.sflpro.cafemanager.table.controller.model.request.CreatTableRequest;
import com.sflpro.cafemanager.table.domain.model.TableModel;
import com.sflpro.cafemanager.table.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("tables")
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;

    @PostMapping
    public TableModel createTable(@Valid @RequestBody CreatTableRequest request) {
        return tableService.createTable(request.getNumber());
    }
}
