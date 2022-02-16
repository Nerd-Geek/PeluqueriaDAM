package ies.luisvives.serverpeluqueriadam.dto.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDTO {
    private String id;
    private String image;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
}
