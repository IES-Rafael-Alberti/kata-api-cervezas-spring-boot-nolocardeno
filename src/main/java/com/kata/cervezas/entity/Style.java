package com.kata.cervezas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "styles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Style {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cat_id")
    private Integer catId;

    @Column(name = "style_name", nullable = false)
    private String styleName;

    @Column(name = "last_mod")
    private LocalDateTime lastMod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", insertable = false, updatable = false)
    private Category category;
}
