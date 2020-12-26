package com.sflpro.cafemanager.user;

import com.sflpro.cafemanager.AbstractSpringIntegrationTest;
import com.sflpro.cafemanager.exception.NotFoundException;
import com.sflpro.cafemanager.table.domain.entity.Table;
import com.sflpro.cafemanager.table.model.TableTestDataBuilder;
import com.sflpro.cafemanager.table.repository.TableRepository;
import com.sflpro.cafemanager.user.controller.model.request.AssignTableRequest;
import com.sflpro.cafemanager.user.controller.model.request.CreatUserRequest;
import com.sflpro.cafemanager.user.domain.entity.User;
import com.sflpro.cafemanager.user.domain.enums.UserRole;
import com.sflpro.cafemanager.user.domain.model.UserModel;
import com.sflpro.cafemanager.user.model.UserTestDataBuilder;
import com.sflpro.cafemanager.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.List;

import static com.sflpro.cafemanager.security.UserRole.MANAGER_ROLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserIT extends AbstractSpringIntegrationTest {
    public static final String CREATE_USER_URL = "/users";
    public static final String ASSIGN_TABLE_TO_USER_URL = "/users/assign";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TableRepository tableRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        tableRepository.deleteAll();
    }

    @ParameterizedTest
    @CsvSource({"ROLE_MANAGER, john, password1", "ROLE_WAITER, arthur, password2"})
    @WithMockUser(roles = MANAGER_ROLE)
    void shouldCreateUserAndReturnMappedUserInResponse(UserRole role, String username, String password) throws Exception {
        CreatUserRequest request = new CreatUserRequest()
                .setRole(role)
                .setUsername(username)
                .setPassword(password);

        String requestJson = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(post(CREATE_USER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserModel actualUserModel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserModel.class);

        assertThat(actualUserModel.getId()).isNotNull().isGreaterThan(0);
        assertThat(actualUserModel.getRole()).isEqualTo(role);
        assertThat(actualUserModel.getUsername()).isEqualTo(username);

        List<User> dbUsers = userRepository.findAll();

        assertThat(dbUsers).hasSize(1);

        User dbUser = dbUsers.get(0);

        assertThat(dbUser.getId()).isNotNull().isGreaterThan(0);
        assertThat(dbUser.getRole()).isEqualTo(role);
        assertThat(dbUser.getUsername()).isEqualTo(username);
    }

    @Test
    @Transactional
    @WithMockUser(roles = MANAGER_ROLE)
    void testAssignTableToWaiter() throws Exception {
        User waiter = UserTestDataBuilder.aWaiter()
                .withUsername("james")
                .build();
        userRepository.save(waiter);

        Table table = TableTestDataBuilder.anUnassignedTable().build();
        tableRepository.save(table);

        AssignTableRequest request = new AssignTableRequest()
                .setUserId(waiter.getId())
                .setTableId(table.getId());

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(ASSIGN_TABLE_TO_USER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Table dbTable = tableRepository.findById(table.getId())
                .orElseThrow(() -> new NotFoundException("Table not found."));

        assertThat(dbTable.getUser()).isEqualTo(waiter);
    }
}