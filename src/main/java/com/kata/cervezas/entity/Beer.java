package com.kata.cervezas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "beers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "brewery_id")
    private Integer breweryId;

    @Column(nullable = false)
    private String name;

    @Column(name = "cat_id")
    private Integer catId;

    @Column(name = "style_id")
    private Integer styleId;

    private Float abv;

    private Float ibu;

    private Float srm;

    private Long upc;

    private String filepath;

    @Column(columnDefinition = "TEXT")
    private String descript;

    @Column(name = "add_user")
    private Integer addUser;

    @Column(name = "last_mod")
    private LocalDateTime lastMod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brewery_id", insertable = false, updatable = false)
    private Brewery brewery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", insertable = false, updatable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id", insertable = false, updatable = false)
    private Style style;
}
