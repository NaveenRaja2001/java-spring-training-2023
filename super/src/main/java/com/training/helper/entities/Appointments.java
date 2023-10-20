package com.training.helper.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Bean class for appointment
 * @Author Naveen Raja
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User resident;

    @ManyToOne
    @JoinColumn(name = "slots_id")
    private Slots slots;

    private LocalDate localDate;

    private Integer helperId;

    public Appointments(User resident, Slots timeSlot, LocalDate localDate, Integer helperId) {
        this.resident=resident;
        this.slots=timeSlot;
        this.localDate=localDate;
        this.helperId=helperId;
    }
}
