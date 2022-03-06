package ies.luisvives.serverpeluqueriadam.controller;

import ies.luisvives.serverpeluqueriadam.config.APIConfig;
import ies.luisvives.serverpeluqueriadam.dto.service.ServiceDTO;
import ies.luisvives.serverpeluqueriadam.exceptions.GeneralBadRequestException;
import ies.luisvives.serverpeluqueriadam.exceptions.ServiceNotFoundException;
import ies.luisvives.serverpeluqueriadam.exceptions.service.ServiceBadRequestException;
import ies.luisvives.serverpeluqueriadam.exceptions.service.ServicesNotFoundException;
import ies.luisvives.serverpeluqueriadam.mapper.ServiceMapper;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/services")
public class ServiceController {

	private final ServiceRepository serviceRepository;
	private final ServiceMapper serviceMapper;

	@Autowired
	public ServiceController(ServiceRepository repository, ServiceMapper serviceMapper) {
		this.serviceRepository = repository;
		this.serviceMapper = serviceMapper;
	}

	@GetMapping("/all")
	public ResponseEntity<List<ServiceDTO>> findAll(@RequestParam(name = "limit") Optional<String> limit) {
		List<Service> services = null;
		try {
			services = serviceRepository.findAll();

			if (limit.isPresent() && !services.isEmpty() && services.size() > Integer.parseInt(limit.get())) {
				return ResponseEntity.ok(serviceMapper.toDTO(services.subList(0, Integer.parseInt(limit.get()))));
			} else {
				if (!services.isEmpty()) {
					return ResponseEntity.ok(serviceMapper.toDTO(services));
				} else {
					throw new ServicesNotFoundException();
				}
			}
		} catch (Exception e) {
			throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
		}
	}

	@GetMapping("/")
	public ResponseEntity<?> findByNameContainsIgnoreCase(@RequestParam(name = "searchQuery") Optional<String> searchQuery
	) {
		List<Service> services;
		try {
			if (searchQuery.isPresent()) {
				services = serviceRepository.findByNameContainsIgnoreCase(searchQuery);
			}else {
				services = serviceRepository.findAll();
			}
			return ResponseEntity.ok(services);
		} catch (Exception e) {
			throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ServiceDTO> findById(@PathVariable String id) {
		Service service = serviceRepository.findById(id).orElse(null);
		if (service == null) {
			throw new ServiceNotFoundException(id);
		} else {
			return ResponseEntity.ok(serviceMapper.toDTO(service));
		}
	}

	@PostMapping("/")
	public ResponseEntity<ServiceDTO> newService(@RequestBody ServiceDTO newService) {
		Service service = serviceMapper.fromDTO(newService);
		checkServiceData(service);
		Service serviceInsert = serviceRepository.save(service);
		return ResponseEntity.ok(serviceMapper.toDTO(serviceInsert));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ServiceDTO> update(@PathVariable String id, @RequestBody ServiceDTO newService) {
		try {
			Service serviceUpdated = serviceRepository.findById(id).orElse(null);
			if (serviceUpdated == null) {
				throw new ServiceNotFoundException(id);
			} else {
				checkServiceData(serviceUpdated);

				serviceUpdated.setName(newService.getName());
				serviceUpdated.setDescription(newService.getDescription());
				serviceUpdated.setImage(newService.getImage());
				serviceUpdated.setPrice(newService.getPrice());
				serviceUpdated.setStock(newService.getStock());
				serviceUpdated = serviceRepository.save(serviceUpdated);

				return ResponseEntity.ok(serviceMapper.toDTO(serviceUpdated));
			}
		} catch (Exception e) {
			throw new GeneralBadRequestException("Actualizar", "Error al actualizar el service. Campos incorrectos.");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceDTO> deleteService(@PathVariable String id) {

			Service service = serviceRepository.findById(id).orElse(null);
			if (service == null) {
				throw new ServiceNotFoundException(id);
			}
			try {
				serviceRepository.delete(service);
				return ResponseEntity.ok(serviceMapper.toDTO(service));
		} catch (Exception e) {
			throw new GeneralBadRequestException("Eliminar", "Error al borrar el service");
		}
	}

	private void checkServiceData(Service service) {
		if (service.getName() == null || service.getName().isEmpty()) {
			throw new ServiceBadRequestException("Nombre", "El nombre es obligatorio");
		}
		if (service.getPrice() <= 0) {
			throw new ServiceBadRequestException("Precio", "El precio debe ser mayor que 0");
		}
		if (service.getStock() < 0) {
			throw new ServiceBadRequestException("Stock", "El stock debe ser mayor o igual que 0");
		}
	}
}
