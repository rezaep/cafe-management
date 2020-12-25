package com.sflpro.cafemanager.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sflpro.cafemanager.AbstractIT;
import com.sflpro.cafemanager.user.controller.model.request.CreatUserRequest;
import com.sflpro.cafemanager.user.domain.entity.User;
import com.sflpro.cafemanager.user.domain.enums.UserRole;
import com.sflpro.cafemanager.user.domain.model.UserModel;
import com.sflpro.cafemanager.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserIT extends AbstractIT {
    public static final String CREATE_USER_URL = "/users";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @ParameterizedTest
    @CsvSource({"MANAGER, john", "WAITER, arthur"})
    void shouldCreateUserAndReturnMappedUserInResponse(UserRole role, String username) throws Exception {
        CreatUserRequest request = new CreatUserRequest()
                .setRole(role)
                .setUsername(username);

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
}