package ies.luisvives.serverpeluqueriadam.mapper;

import ies.luisvives.serverpeluqueriadam.dto.service.ServiceDTO;
import ies.luisvives.serverpeluqueriadam.model.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServiceMapper {
    private final ModelMapper modelMapper;

    public ServiceDTO toDTO(Service service) {
        return modelMapper.map(service, ServiceDTO.class);

    }

    public Service fromDTO(ServiceDTO serviceDTO) {
        return modelMapper.map(serviceDTO, Service.class);
    }

    public List<ServiceDTO> toDTO(List<Service> services) {
        return services.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
