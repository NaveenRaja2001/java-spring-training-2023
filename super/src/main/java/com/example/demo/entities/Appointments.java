package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

//    @ManyToOne
//    @JoinColumn(name = "helper_details_id")
//    private HelperDetails helperDetails;
    private LocalDate localDate;

    private Integer helperId;
}
