package ies.luisvives.serverpeluqueriadam.dto.service;

import ies.luisvives.serverpeluqueriadam.model.Appointment;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private String id;
    private String image;
    @NotBlank(message = "El nombre del servicio no puede estar vacío")
    private String name;
    private String description;
    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double price;
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}
