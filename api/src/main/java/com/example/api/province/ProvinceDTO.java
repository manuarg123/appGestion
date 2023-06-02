package com.example.api.province;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinceDTO {
    private String name;

    public ProvinceDTO(){}

    public ProvinceDTO(String name) {
        this.name = name;
    }
}
