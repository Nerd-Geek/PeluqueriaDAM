package ies.luisvives.serverpeluqueriadam.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentDTO {
    private String id;
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDate date;
    @NotNull(message = "El tiempo no puede ser nulo")
    private LocalTime time;
    @NotBlank(message = "El usuario no puede ser nulo")
    private String userId;
    @NotBlank(message = "El servicio no puede ser nulo")
    private String serviceId;

    public CreateAppointmentDTO(LocalDate date, LocalTime time, String userId, String serviceId) {
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.time = time;
        this.userId = userId;
        this.serviceId = serviceId;
    }
}
