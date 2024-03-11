package com.samdev.jwt_auth.Repository;

import com.samdev.jwt_auth.Entity.UserAccountVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountVerify, Long> {

    Optional<UserAccountVerify> findByEmail(String email);
}
