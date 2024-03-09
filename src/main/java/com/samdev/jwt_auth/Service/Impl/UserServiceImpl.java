package com.samdev.jwt_auth.Service.Impl;

import com.samdev.jwt_auth.DAO.UserDAO;
import com.samdev.jwt_auth.Entity.User;
import com.samdev.jwt_auth.Exceptions.UserAlreadyExistsException;
import com.samdev.jwt_auth.Exceptions.UserDoesNotExistsException;
import com.samdev.jwt_auth.Repository.UserRepository;
import com.samdev.jwt_auth.Service.JwtService;
import com.samdev.jwt_auth.Service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDAO userRegister(UserDAO userDAO) {
        UserDAO userDAO1 = new UserDAO();
        User user = new User();


        try {
            if(userRepository.existsByEmail(userDAO.getEmail())){

                throw new UserAlreadyExistsException("User already exists");
            }
            user.setEmail(userDAO.getEmail());
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
        }catch (Exception e){
            userDAO1.setResponseMessage("error occurred");
            userDAO1.setResponseCode("400");
            return userDAO1;
        }
    }

    @Override
    public UserDAO userLogin(UserDAO userDAO) {
        UserDAO userDAO1 = new UserDAO();

        try{
            if(!userRepository.existsByEmail(userDAO.getEmail())){
                throw new UserDoesNotExistsException("User does not exist");
            }else {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDAO.getEmail(),userDAO.getPassword()));

                User user = userRepository.findByEmail(userDAO.getEmail()).orElseThrow();

                String token = jwtService.generateToken(user);
                userDAO1.setResponseCode("200");
                userDAO1.setToken(token);
                userDAO1.setResponseMessage("Login successfully!");

                return userDAO1;
            }

        }catch (UserDoesNotExistsException e){
            userDAO1.setResponseMessage(e.getMessage());
            userDAO1.setResponseCode("400");

            return userDAO1;
        }catch (Exception e){
            userDAO1.setResponseMessage("Internal Server Error");
            userDAO1.setResponseCode("500");

            return userDAO1;
        }
    }
}
