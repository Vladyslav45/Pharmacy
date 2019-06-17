package com.medicine.pharmacy.service;

import com.medicine.pharmacy.model.User;

import java.util.List;

public interface UserService {

    User findUserByEmail(String email);

    void saveUser(User user);

    User findById(Long id);

    List<User> findAllByRole(String role);

    User update(User user);
}
