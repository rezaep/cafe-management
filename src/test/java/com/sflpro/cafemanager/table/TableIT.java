package com.sflpro.cafemanager.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sflpro.cafemanager.AbstractSpringIntegrationTest;
import com.sflpro.cafemanager.table.controller.model.request.CreatTableRequest;
import com.sflpro.cafemanager.table.controller.model.response.AssignedTablesResponse;
import com.sflpro.cafemanager.table.domain.entity.Table;
import com.sflpro.cafemanager.table.domain.model.AssignedTableModel;
import com.sflpro.cafemanager.table.domain.model.TableModel;
import com.sflpro.cafemanager.table.model.TableTestDataBuilder;
import com.sflpro.cafemanager.table.repository.TableRepository;
import com.sflpro.cafemanager.user.domain.entity.User;
import com.sflpro.cafemanager.user.model.UserTestDataBuilder;
import com.sflpro.cafemanager.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TableIT extends AbstractSpringIntegrationTest {
    public static final String CREATE_TABLE_URL = "/tables";
    public static final String GET_ASSIGNED_TABLES_URL = "/tables/assigned/{userId}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        tableRepository.deleteAll();
        userRepository.deleteAll();
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

    @Test
    void shouldGetAssignedTableMapAndReturnAssignedTablesToThisUser() throws Exception {
        User waiter = UserTestDataBuilder.aWaiter().build();
        userRepository.save(waiter);

        Table table1 = TableTestDataBuilder.aTable().withNumber(1).build();
        Table table2 = TableTestDataBuilder.aTable().withNumber(2).build();
        Table table3 = TableTestDataBuilder.aTable().withNumber(3).withUser(waiter).build();
        Table table4 = TableTestDataBuilder.aTable().withNumber(4).withUser(waiter).build();

        List<Table> savedTables = List.of(table1, table2, table3, table4);

        tableRepository.saveAll(savedTables);

        MvcResult mvcResult = mockMvc.perform(get(GET_ASSIGNED_TABLES_URL, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        AssignedTablesResponse assignedTables = objectMapper.readValue(mvcResult.getResponse().getContentAsString()
                , AssignedTablesResponse.class);

        assertThat(assignedTables.getItems()).hasSize(2)
                .containsExactlyInAnyOrder(
                        new AssignedTableModel(table3.getId(), table3.getNumber()),
                        new AssignedTableModel(table4.getId(), table4.getNumber())
                );
    }
}