package ies.luisvives.serverpeluqueriadam.exceptions.storage;

public class StorageFileNotFoundException extends StorageException {
    private static final long serialVersionUID = 86546786467580532L;

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}