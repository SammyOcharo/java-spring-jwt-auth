package com.samdev.jwt_auth.Service;

import com.samdev.jwt_auth.DAO.UserDAO;

public interface UserService {
    UserDAO userRegister(UserDAO userDAO);
}
