package com.security.demoJWT.repo;

import com.security.demoJWT.entity.TokenExpired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenExpiredRepository extends JpaRepository<TokenExpired,Integer> {

    boolean existsByToken(String token);
}
