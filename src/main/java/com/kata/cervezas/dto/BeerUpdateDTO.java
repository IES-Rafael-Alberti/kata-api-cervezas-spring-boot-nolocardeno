package com.kata.cervezas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerUpdateDTO {
    private Integer breweryId;
    private String name;
    private Integer catId;
    private Integer styleId;
    private Float abv;
    private Float ibu;
    private Float srm;
    private Long upc;
    private String descript;
}
