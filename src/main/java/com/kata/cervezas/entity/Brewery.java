package com.kata.cervezas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "breweries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brewery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    private String city;

    private String state;

    private String code;

    private String country;

    private String phone;

    private String website;

    private String filepath;

    @Column(columnDefinition = "TEXT")
    private String descript;

    @Column(name = "add_user")
    private Integer addUser;

    @Column(name = "last_mod")
    private LocalDateTime lastMod;
}
