package com.sflpro.cafemanager.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sflpro.cafemanager.AbstractIT;
import com.sflpro.cafemanager.table.controller.model.request.CreatTableRequest;
import com.sflpro.cafemanager.table.domain.entity.Table;
import com.sflpro.cafemanager.table.domain.model.TableModel;
import com.sflpro.cafemanager.table.repository.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TableIT extends AbstractIT {
    public static final String CREATE_TABLE_URL = "/tables";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TableRepository tableRepository;

    @BeforeEach
    public void setup() {
        tableRepository.deleteAll();
    }

    @Test
    void shouldCreateTableAndReturnMappedTableInResponse() throws Exception {
        CreatTableRequest request = new CreatTableRequest()
                .setNumber(1);

        String requestJson = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(post(CREATE_TABLE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TableModel actualTableModel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TableModel.class);

        assertThat(actualTableModel.getId()).isNotNull().isGreaterThan(0);
        assertThat(actualTableModel.getNumber()).isEqualTo(request.getNumber());
        assertThat(actualTableModel.getUserId()).isNull();

        List<Table> dbTables = tableRepository.findAll();

        assertThat(dbTables).hasSize(1);

        Table dbTable = dbTables.get(0);

        assertThat(dbTable.getId()).isNotNull().isGreaterThan(0);
        assertThat(dbTable.getNumber()).isEqualTo(request.getNumber());
        assertThat(dbTable.getUser()).isNull();
    }
}