package com.sflpro.cafemanager.user.service;

import com.sflpro.cafemanager.exception.AlreadyExistException;
import com.sflpro.cafemanager.exception.NotFoundException;
import com.sflpro.cafemanager.table.service.TableService;
import com.sflpro.cafemanager.user.domain.entity.User;
import com.sflpro.cafemanager.user.domain.enums.UserRole;
import com.sflpro.cafemanager.user.domain.model.UserModel;
import com.sflpro.cafemanager.user.exception.WrongUserTypeException;
import com.sflpro.cafemanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TableService tableService;

    public UserModel createUser(UserRole role, String username) {
        if (userRepository.existsUserByUsername(username)) {
            throw new AlreadyExistException("A user with the given username already exists.");
        }

        User user = new User()
                .setRole(role)
                .setUsername(username);

        userRepository.save(user);

        return convertToModel(user);
    }

    private User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void assignTableToUser(long userId, long tableId) {
        User user = findUserById(userId);

        if (!UserRole.WAITER.equals(user.getRole())) {
            throw new WrongUserTypeException();
        }

        tableService.assignTableToUser(tableId, user);
    }

    private UserModel convertToModel(User user) {
        return new UserModel(user.getId(), user.getRole(), user.getUsername());
    }
}
