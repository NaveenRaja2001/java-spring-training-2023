//package com.security.demoJWT.user;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@Entity
//@Table(name = "roles")
//public class Roles {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(nullable = false, length = 50, unique = true)
//    private String name;
//
//    public Roles() { }
//
//    public Roles(String name) {
//        this.name = name;
//    }
//
//    public Roles(Integer id) {
//        this.id = id;
//    }
//
//
//    @Override
//    public String toString() {
//        return this.name;
//    }
//
//    // getters and setters are not shown for brevity
//}