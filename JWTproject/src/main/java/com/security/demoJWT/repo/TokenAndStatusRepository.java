package com.security.demoJWT.repo;

import com.security.demoJWT.entity.TokenAndStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenAndStatusRepository extends JpaRepository<TokenAndStatus,Integer> {
    boolean existsByToken(String token);

    TokenAndStatus findByToken(String token);
}
