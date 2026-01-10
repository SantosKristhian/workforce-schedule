package com.shiftflow.user.service;

import com.shiftflow.user.entity.User;
import com.shiftflow.user.entity.UserRole;
import com.shiftflow.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String name, String email, String password, UserRole role, User currentUser) {

        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("Only ADMIN can create users");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setActive(true);

        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User deactivateUser(Long id, User currentUser) {

        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("Only ADMIN can deactivate users");
        }

        User user = getById(id);
        user.setActive(false);
        return userRepository.save(user);
    }
}
