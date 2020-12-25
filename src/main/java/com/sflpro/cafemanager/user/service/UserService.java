package com.sflpro.cafemanager.user.service;

import com.sflpro.cafemanager.user.domain.entity.User;
import com.sflpro.cafemanager.user.domain.enums.UserRole;
import com.sflpro.cafemanager.user.domain.model.UserModel;
import com.sflpro.cafemanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserModel createUser(UserRole role, String username) {
        User user = new User()
                .setRole(role)
                .setUsername(username);

        userRepository.save(user);

        return convertToModel(user);
    }

    private UserModel convertToModel(User user) {
        return new UserModel(user.getId(), user.getRole(), user.getUsername());
    }
}
