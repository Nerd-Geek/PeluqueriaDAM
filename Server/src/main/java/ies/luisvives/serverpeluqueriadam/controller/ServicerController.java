package ies.luisvives.serverpeluqueriadam.controller;

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
public class ServicerController {

	private final ServiceRepository repository;
	private final ServiceMapper serviceMapper;

	@Autowired
	public ServicerController (ServiceRepository repository, ServiceMapper serviceMapper) {
		this.repository = repository;
		this.serviceMapper = serviceMapper;
	}

	@GetMapping("/services")
	public ResponseEntity<?> findAll(@RequestParam(name = "limit") Optional<String> limit) {
		List<Service> services = null;
		try {
			services = repository.findAll();

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

	@GetMapping("/services/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		Service service = repository.findById(id).orElse(null);
		if (service == null) {
			throw new ServiceNotFoundException(id);
		} else {
			return ResponseEntity.ok(serviceMapper.toDTO(service));
		}
	}

	@PostMapping("/services")
	public ResponseEntity<?> newService(@RequestBody ServiceDTO newService) {
		Service service = serviceMapper.fromDTO(newService);
		checkServiceData(service);
		Service serviceInsert = repository.save(service);
		return ResponseEntity.ok(serviceMapper.toDTO(serviceInsert));
	}

	@PutMapping("/services/{id}")
	public ResponseEntity<?> update(@RequestBody Service newService, @PathVariable String id) {
		try {
			Service serviceUpdated = repository.findById(id).orElse(null);
			if (serviceUpdated == null) {
				throw new ServiceNotFoundException(id);
			} else {
				checkServiceData(newService);

				serviceUpdated.setName(newService.getName());
				serviceUpdated.setDescription(newService.getDescription());
				serviceUpdated.setImage(newService.getImage());
				serviceUpdated.setPrice(newService.getPrice());
				serviceUpdated.setStock(newService.getStock());
				serviceUpdated = repository.save(serviceUpdated);

				return ResponseEntity.ok(serviceMapper.toDTO(serviceUpdated));
			}
		} catch (Exception e) {
			throw new GeneralBadRequestException("Actualizar", "Error al actualizar el service. Campos incorrectos.");
		}
	}

	@DeleteMapping("/services/{id}")
	public ResponseEntity<ServiceDTO> deleteService(@PathVariable String id) {
		try {
			Service service = repository.findById(id).orElse(null);
			if (service == null) {
				throw new ServiceNotFoundException(id);
			} else {
				repository.delete(service);
				return ResponseEntity.ok(serviceMapper.toDTO(service));
			}
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
