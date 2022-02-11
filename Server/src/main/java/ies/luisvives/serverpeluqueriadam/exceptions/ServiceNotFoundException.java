package ies.luisvives.serverpeluqueriadam.exceptions;

public class ServiceNotFoundException extends RuntimeException {
	public ServiceNotFoundException(String id) {
		super("Could not find service "+ id);
	}
}
