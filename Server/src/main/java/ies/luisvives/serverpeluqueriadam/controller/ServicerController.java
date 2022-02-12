package ies.luisvives.serverpeluqueriadam.controller;

import ies.luisvives.serverpeluqueriadam.dto.ServiceDTO;
import ies.luisvives.serverpeluqueriadam.exceptions.ServiceNotFoundException;
import ies.luisvives.serverpeluqueriadam.mapper.ServiceMapper;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public ResponseEntity<?> all() {
		return ResponseEntity.ok(serviceMapper.toDTO(repository.findAll()));
	}

	@GetMapping("/services/{id}")
	public ResponseEntity<?> one(@PathVariable String id) {
		Service service = repository.findById(id).orElse(null);
		return ResponseEntity.ok(serviceMapper.toDTO(service));
	}

	@PostMapping("/services")
	public ResponseEntity<?> newService(@RequestBody ServiceDTO newService) {
		Service service = serviceMapper.fromDTO(newService);
		Service serviceInsert = repository.save(service);
		return ResponseEntity.ok(serviceMapper.toDTO(serviceInsert));
	}

	@PutMapping("/services/{id}")
	public ResponseEntity<?> replaceService(@RequestBody Service newService, @PathVariable String id) {
		Service serviceUpdated = repository.findById(id).orElse(null);

		serviceUpdated.setName(newService.getName());
		serviceUpdated.setDescription(newService.getDescription());
		serviceUpdated.setImage(newService.getImage());
		serviceUpdated.setPrice(newService.getPrice());
		serviceUpdated.setStock(newService.getStock());
		serviceUpdated = repository.save(serviceUpdated);
		return ResponseEntity.ok(serviceMapper.toDTO(serviceUpdated));
	}

	@DeleteMapping("/services/{id}")
	public void deleteService(@PathVariable String id) {
		repository.deleteById(id);
	}
}
