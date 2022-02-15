package ies.luisvives.serverpeluqueriadam.exceptions.storage;

public class StorageException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;
    public StorageException(String message) {
        super(message);
    }
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}