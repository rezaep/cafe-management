package com.sflpro.cafemanager.table.service;

import com.sflpro.cafemanager.exception.AlreadyExistException;
import com.sflpro.cafemanager.table.domain.entity.Table;
import com.sflpro.cafemanager.table.domain.model.TableModel;
import com.sflpro.cafemanager.table.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;

    public TableModel createTable(int number) {
        if (tableRepository.existsByNumber(number)) {
            throw new AlreadyExistException("A table with the given number already exists.");
        }

        Table table = new Table()
                .setNumber(number);

        tableRepository.save(table);

        return convertToModel(table);
    }

    private TableModel convertToModel(Table table) {
        TableModel model = new TableModel()
                .setId(table.getId())
                .setNumber(table.getNumber());

        if (!ObjectUtils.isEmpty(table.getUser())) {
            model.setUserId(table.getUser().getId());
        }

        return model;
    }
}
