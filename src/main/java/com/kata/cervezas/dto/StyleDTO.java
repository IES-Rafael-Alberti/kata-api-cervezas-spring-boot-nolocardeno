package com.kata.cervezas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StyleDTO {
    private Integer id;
    private Integer catId;
    private String styleName;
    private String categoryName;
}
