package com.sflpro.cafemanager.user.controller;

import com.sflpro.cafemanager.user.controller.model.request.AssignTableRequest;
import com.sflpro.cafemanager.user.controller.model.request.CreatUserRequest;
import com.sflpro.cafemanager.user.domain.enums.UserRole;
import com.sflpro.cafemanager.user.domain.model.UserModel;
import com.sflpro.cafemanager.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @Secured(UserRole.Code.MANAGER)
    public UserModel createUser(@Valid @RequestBody CreatUserRequest request) {
        return userService.createUser(request.getRole(), request.getUsername(), request.getPassword());
    }

    @PostMapping("assign")
    @Secured(UserRole.Code.MANAGER)
    public void assignTableToWaiter(@Valid @RequestBody AssignTableRequest request) {
        userService.assignTableToUser(request.getUserId(), request.getTableId());
    }
}
