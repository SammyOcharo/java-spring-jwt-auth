package com.samdev.jwt_auth.Service.Impl;

import com.samdev.jwt_auth.DAO.UserDAO;
import com.samdev.jwt_auth.Entity.User;
import com.samdev.jwt_auth.Exceptions.UserAlreadyExistsException;
import com.samdev.jwt_auth.Repository.UserRepository;
import com.samdev.jwt_auth.Service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDAO userRegister(UserDAO userDAO) {
        UserDAO userDAO1 = new UserDAO();
        User user = new User();

        if(userRepository.existsByEmail(userDAO.getEmail())){
            throw new UserAlreadyExistsException("User already exists");
        }

        try {

            user.setEmail(user.getEmail());
            user.setMobileNumber(userDAO.getMobileNumber());
            user.setRole(userDAO.getRole());
            user.setPassword(passwordEncoder.encode(userDAO.getPassword()));

            userRepository.save(user);

            userDAO1.setResponseCode("200");
            userDAO1.setResponseMessage("User registered successfully!");

            return userDAO1;

        }catch (UserAlreadyExistsException e){
            userDAO1.setResponseMessage(e.getMessage());
            userDAO1.setResponseCode("400");
            return userDAO1;
        }
    }
}
