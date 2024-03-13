package com.samdev.jwt_auth.Service.Impl;

import com.samdev.jwt_auth.DAO.UserDAO;
import com.samdev.jwt_auth.Entity.User;
import com.samdev.jwt_auth.Entity.UserAccountVerify;
import com.samdev.jwt_auth.Exceptions.OtpMissMatch;
import com.samdev.jwt_auth.Exceptions.UserAlreadyExistsException;
import com.samdev.jwt_auth.Exceptions.UserDoesNotExistsException;
import com.samdev.jwt_auth.Repository.UserAccountRepository;
import com.samdev.jwt_auth.Repository.UserRepository;
import com.samdev.jwt_auth.Service.JwtService;
import com.samdev.jwt_auth.Service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder, UserAccountRepository userAccountRepository,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAccountRepository = userAccountRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
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

            Random random = new Random();
            int otp = random.nextInt(10000) + 1000;

            String subject = "Activate account OTP";
            String body = "Use this otp " + otp + " to activate your account";

            emailService.sendEmail(userDAO.getEmail(), subject,  body);

            UserAccountVerify userAccountVerify = new UserAccountVerify();
            userAccountVerify.setEmail(userDAO.getEmail());
            userAccountVerify.setOtp(otp);

            userAccountRepository.save(userAccountVerify);

            userDAO1.setResponseCode("200");
            userDAO1.setResponseMessage("User registered successfully. Account verification otp sent to email!");

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

        }catch (AuthenticationException e) {
            userDAO1.setResponseMessage("Incorrect username or password!");
            userDAO1.setResponseCode("400");

            return userDAO1;
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

    @Override
    public UserDAO userVerifyAccount(UserDAO userDAO) {
        UserDAO userDAO1 = new UserDAO();
        try{
            if(!userRepository.existsByEmail(userDAO.getEmail())){
                throw new UserDoesNotExistsException("User with email" + userDAO.getEmail()+" does not exist");
            }else {
                UserAccountVerify userAccountVerify= userAccountRepository.findByEmail(userDAO.getEmail())
                                .orElseThrow(()->  new UserDoesNotExistsException("User does not exist"));

                if(!userAccountVerify.getOtp().equals(userDAO.getOtp())){
                    throw new OtpMissMatch("Otp mismatch!");
                }

                User user = userRepository.findByEmail(userDAO.getEmail()).orElseThrow();

                user.setAccountActivated(Boolean.TRUE);

                userRepository.save(user);

                userAccountVerify.setUsed(Boolean.TRUE);
                userAccountRepository.save(userAccountVerify);


                userDAO1.setResponseCode("200");
                userDAO1.setResponseMessage("Account verified!");

                return userDAO1;
            }
        }catch (OtpMissMatch | UserDoesNotExistsException e){
            userDAO1.setResponseMessage(e.getMessage());
            userDAO1.setResponseCode("400");

            return userDAO1;
        } catch (Exception e){
            userDAO1.setResponseMessage("Internal Server Error");
            userDAO1.setResponseCode("500");

            return userDAO1;
        }
    }
}
