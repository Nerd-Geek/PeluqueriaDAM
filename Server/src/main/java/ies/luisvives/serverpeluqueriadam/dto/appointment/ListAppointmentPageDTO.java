package ies.luisvives.serverpeluqueriadam.dto.appointment;

import ies.luisvives.serverpeluqueriadam.config.APIConfig;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ListAppointmentPageDTO {
    private final LocalDateTime query = LocalDateTime.now();
    private final String project = "SpringDam";
    private final String version = APIConfig.API_VERSION;
    private List<AppointmentDTO> data;
    private int currentPage;
    private long totalElements;
    private int totalPages;
}