package com.samdev.jwt_auth.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.samdev.jwt_auth.DAO.UserDAO;
import com.samdev.jwt_auth.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apps/api/v1/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("register/")
    public ResponseEntity<UserDAO> userRegister(@RequestBody UserDAO userDAO){
        return new ResponseEntity<>(userService.userRegister(userDAO), HttpStatus.OK);
    }

    //verify account
    @PostMapping("verify-account/")
    public ResponseEntity<UserDAO> userVerifyAccount(@RequestBody UserDAO userDAO){
        return new ResponseEntity<>(userService.userVerifyAccount(userDAO), HttpStatus.OK);
    }
    @PostMapping("login/")
    public ResponseEntity<UserDAO> userLogin(@RequestBody UserDAO userDAO){
        return new ResponseEntity<>(userService.userLogin(userDAO), HttpStatus.OK);
    }
}
