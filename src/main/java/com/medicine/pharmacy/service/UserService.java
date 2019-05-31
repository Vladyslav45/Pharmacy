package com.medicine.pharmacy.service;

import com.medicine.pharmacy.model.User;

public interface UserService {

    User findUserByEmail(String email);

    void saveUser(User user);

    User findById(Long id);
}
