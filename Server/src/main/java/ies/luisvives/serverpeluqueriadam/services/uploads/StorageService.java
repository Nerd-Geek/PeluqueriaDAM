package ies.luisvives.serverpeluqueriadam.services.uploads;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    /**
     * Inicia el sistema de ficheros
     */
    void init();

    /**
     * Almacena un fichero de contenido multiparte
     * @param file Contenido multiparte
     * @return String
     */
    String store(MultipartFile file);

    /**
     * Devuelve un Stream con el Path de los ficheros
     * @return Stream de Path de los ficheros
     */
    Stream<Path> loadAll();

    /**
     * Devuelve el Path del fichero
     * @param filename nombre del fichero
     * @return Path del fichero
     */
    Path load(String filename);

    /**
     * Devuelve el fichero como recurso
     * @param filename nombre del fichero
     * @return Resource del fichero
     */
    Resource loadAsResource(String filename);

    /**
     * Elimina un fichero
     * @param filename nombre del fichero
     */
    void delete(String filename);

    /**
     * Elimina todos los ficheros
     */
    void deleteAll();

    /**
     * Obtiene la URL del fichero dado el nombre del archivo
     * @param filename nombre del archivo
     * @return Url del fichero
     */
    String getUrl(String filename);
}