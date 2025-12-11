package com.kata.cervezas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerCreateDTO {
    
    private Integer breweryId;
    
    @NotBlank(message = "El nombre de la cerveza es obligatorio")
    private String name;
    
    private Integer catId;
    
    private Integer styleId;
    
    private Float abv;
    
    private Float ibu;
    
    private Float srm;
    
    private Long upc;
    
    private String descript;
}
