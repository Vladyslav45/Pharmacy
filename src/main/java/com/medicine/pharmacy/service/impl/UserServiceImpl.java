package com.medicine.pharmacy.service.impl;

import com.medicine.pharmacy.model.Role;
import com.medicine.pharmacy.model.User;
import com.medicine.pharmacy.repository.RoleRepository;
import com.medicine.pharmacy.repository.UserRepository;
import com.medicine.pharmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void
    saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role role = roleRepository.findByRole("ROLE_USER");
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<User> findAllByRole(String role) {
        return userRepository.findAllByRoleRole(role);
    }
}
