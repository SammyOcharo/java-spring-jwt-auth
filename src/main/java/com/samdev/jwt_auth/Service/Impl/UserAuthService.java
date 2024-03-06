package com.samdev.jwt_auth.Service.Impl;

import com.samdev.jwt_auth.Entity.User;
import com.samdev.jwt_auth.Repository.UserAuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    private final UserAuthRepository userAuthRepository;

    public UserAuthService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("username not found"));
    }
}
