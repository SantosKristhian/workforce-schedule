package com.shiftflow.shiftflow.user.service;

import com.shiftflow.shiftflow.user.entity.User;
import com.shiftflow.shiftflow.user.entity.UserRole;
import com.shiftflow.shiftflow.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email, String password, UserRole role) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User deactivateUser(Long id) {
        User user = getById(id);
        user.setActive(false);
        return userRepository.save(user);
    }


}
