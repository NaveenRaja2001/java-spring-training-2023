package com.training.helper.repository;

import com.training.helper.entities.TokenExpired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenExpiredRepository extends JpaRepository<TokenExpired,Integer> {
    boolean existsByToken(String token);
}
