package com.training.helper.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean class for expired token
 *
 * @Author Naveen Raja
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expiredToken")
public class TokenExpired {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
}