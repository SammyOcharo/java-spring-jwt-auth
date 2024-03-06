package com.samdev.jwt_auth.Repository;

import com.samdev.jwt_auth.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<User, Long> {
    Optional<UserDetails> findByEmail(String email);
}
