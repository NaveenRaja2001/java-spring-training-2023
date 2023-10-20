package com.training.helper.repository;

import com.training.helper.entities.TokenExpired;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *  Interface for token expired entity
 *
 * @Author Naveen Raja
 */
public interface TokenExpiredRepository extends JpaRepository<TokenExpired,Integer> {
    boolean existsByToken(String token);
}
