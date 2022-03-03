package ies.luisvives.serverpeluqueriadam.dto.appointment;

import ies.luisvives.serverpeluqueriadam.config.APIConfig;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ListAppointmentPageDTO {
    private final LocalDateTime query = LocalDateTime.now();
    private final String project = "SpringDam";
    private final String version = APIConfig.API_VERSION;
    @NotNull(message = "La lista de citas no puede ser nula")
    private List<AppointmentDTO> data;
    @NotNull(message = "La paginación no puede ser nula")
    @Min(value = 0, message = "La paginación no puede ser negativa")
    private int currentPage;
    @NotNull(message = "Los elementos totales no pueden ser nulos")
    @Min(value = 0, message = "Los elementos totales no pueden ser negativos")
    private long totalElements;
    @NotNull(message = "EL total de las páginas no pueden ser nulas")
    @Min(value = 0, message = "El total de las páginas no puede ser negativo")
    private int totalPages;
}