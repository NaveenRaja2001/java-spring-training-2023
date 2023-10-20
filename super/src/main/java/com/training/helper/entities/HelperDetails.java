package com.training.helper.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean class for helper details
 *
 * @Author Naveen Raja
 */
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

    //    @Digits(integer=10,fraction = 0,message = "must be a indian phone number")
    private Long phoneNumber;

    private String skill;

    private String status;

    public HelperDetails(Long phonenumber, String skill, String status) {
        this.phoneNumber = phonenumber;
        this.skill = skill;
        this.status = status;
    }

    public HelperDetails(Integer id, Long phonenumber, String skill, String status) {
        this.id = id;
        this.phoneNumber = phonenumber;
        this.skill = skill;
        this.status = status;
    }
}
