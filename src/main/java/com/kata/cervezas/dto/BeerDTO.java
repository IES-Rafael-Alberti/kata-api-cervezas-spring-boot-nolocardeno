package com.kata.cervezas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {
    private Integer id;
    private Integer breweryId;
    private String name;
    private Integer catId;
    private Integer styleId;
    private Float abv;
    private Float ibu;
    private Float srm;
    private Long upc;
    private String filepath;
    private String descript;
    
    // Información adicional de relaciones
    private String breweryName;
    private String categoryName;
    private String styleName;
}
