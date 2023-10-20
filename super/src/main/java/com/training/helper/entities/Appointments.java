package com.training.helper.entities;

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


//   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//   @Past(message = "")
    private LocalDate localDate;
//    @ManyToOne
//    @JoinColumn(name = "helper_details_id")
//    private HelperDetails helperDetails;
    private Integer helperId;

    public Appointments(User resident, Slots timeSlot, LocalDate localDate, Integer helperId) {
        this.resident=resident;
        this.slots=timeSlot;
        this.localDate=localDate;
        this.helperId=helperId;
    }
}
