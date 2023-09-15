package com.security.demoJWT.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String locationName;
    private Boolean activeStatus;
    private String locationType;
    private int availableCount;
//    @OneToOne(mappedBy = "location")
//    private Link link;
}
