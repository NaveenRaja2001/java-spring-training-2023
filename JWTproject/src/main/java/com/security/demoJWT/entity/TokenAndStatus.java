package com.security.demoJWT.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token_and_status")
public class TokenAndStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private Timestamp timestamp;
}
