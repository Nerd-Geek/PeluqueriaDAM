package ies.luisvives.serverpeluqueriadam.restController;

import ies.luisvives.serverpeluqueriadam.exceptions.ServiceNotFoundException;
import ies.luisvives.serverpeluqueriadam.model.Service;
import ies.luisvives.serverpeluqueriadam.repository.ServiceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServicerController {

	private final ServiceRepository repository;

	public ServicerController (ServiceRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/services")
	List<Service> all() {
		return repository.findAll();
	}

	@GetMapping("/services/{id}")
	Service one(@PathVariable String id) {

		return repository.findById(id)
				.orElseThrow(() -> new ServiceNotFoundException(id));
	}

	@PostMapping("/services")
	Service newService(@RequestBody Service newService) {
		return repository.save(newService);
	}

	@PutMapping("/services/{id}")
	Service replaceService(@RequestBody Service newService, @PathVariable String id) {

		return repository.findById(id)
				.map(service -> {
					service.setName(newService.getName());
					service.setDescription(newService.getDescription());
					service.setImage(newService.getImage());
					service.setPrice(newService.getPrice());
					service.setStock(newService.getStock());
					return repository.save(service);
				})
				.orElseThrow(() -> new ServiceNotFoundException(id));
	}

	@DeleteMapping("/services/{id}")
	void deleteService(@PathVariable String id) {
		repository.deleteById(id);
	}
}
