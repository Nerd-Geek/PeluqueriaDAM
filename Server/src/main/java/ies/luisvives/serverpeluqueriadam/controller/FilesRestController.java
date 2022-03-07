package ies.luisvives.serverpeluqueriadam.controller;

import ies.luisvives.serverpeluqueriadam.config.APIConfig;
import ies.luisvives.serverpeluqueriadam.exceptions.storage.StorageException;
import ies.luisvives.serverpeluqueriadam.services.uploads.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(APIConfig.API_PATH + "/files")
public class FilesRestController {
    private StorageService storageService;

    @Autowired
    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    @ApiOperation(value = "Obtiene un fichero en base a su nombre y url", notes = "Devuelve el fichero indicado por fichero por su nombre en url")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Resource.class),
            @ApiResponse(code = 404, message = "Not Found", response = StorageException.class),
    })
    @GetMapping(value = "{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request) {
        Resource file = storageService.loadAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new StorageException("No se puede determinar el tipo de contenido del fichero", ex);
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

    @ApiOperation(value = "Almacena un fichero", notes = "Almacena un fichero en el servidor y devuelve su URL")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class),
            @ApiResponse(code = 400, message = "Bad Request", response = StorageException.class),
    })
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestPart("file") MultipartFile file) {
        String urlImage = null;
        try {
            if (!file.isEmpty()) {
                String image = storageService.store(file);
                urlImage = storageService.getUrl(image);
                Map<String, Object> response = Map.of("url", urlImage);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                throw new StorageException("No se puede subir un fichero vacío");
            }
        } catch (StorageException e) {
            throw new StorageException("No se puede subir un fichero vacío");
        }
    }
}