package com.absolutecinema.service;

import com.absolutecinema.model.Role;
import com.absolutecinema.model.User;
import com.absolutecinema.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void registerUser(String fullName, String email, String username, String passwordHash, Role role) {
        String newId = generateNextId();
        User newUser = new User(newId, fullName, email, username, passwordHash, role);
        userRepository.save(newUser);
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public void deleteUser(String id) {
        userRepository.delete(id);
    }

    public String generateNextId() {
        String lastId = userRepository.getLastId();
        if (lastId == null || lastId.isEmpty() || lastId.equals("USR000")) {
            return "USR001";
        }
        int numericPart = Integer.parseInt(lastId.replace("USR", ""));
        return String.format("USR%03d", numericPart + 1);
    }
}
