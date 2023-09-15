package com.security.demoJWT.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "shows_id")
   private Show show;
    @OneToOne
    @JoinColumn(name = "movies_id")
    private Movie movie;
    @OneToOne
    @JoinColumn(name = "locations_id")
    private Location location;

    private LocalDate date;
}
