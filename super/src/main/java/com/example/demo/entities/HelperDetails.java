package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "helper_details")
public class HelperDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private User user;

    private Long phoneNumber;

    private String skill;

    private String status;

    public HelperDetails(Long phonenumber, String skill, String status) {
        this.phoneNumber=phonenumber;
        this.skill=skill;
        this.status=status;
    }

    public HelperDetails(Integer id, Long phonenumber, String skill, String status) {
        this.id=id;
        this.phoneNumber=phonenumber;
        this.skill=skill;
        this.status=status;
    }
}
