package ies.luisvives.serverpeluqueriadam.controller;

import ies.luisvives.serverpeluqueriadam.config.APIConfig;
import ies.luisvives.serverpeluqueriadam.dto.login.ListLoginPageDTO;
import ies.luisvives.serverpeluqueriadam.dto.login.LoginDTO;
import ies.luisvives.serverpeluqueriadam.exceptions.GeneralBadRequestException;
import ies.luisvives.serverpeluqueriadam.exceptions.login.LoginBadRequestException;
import ies.luisvives.serverpeluqueriadam.exceptions.login.LoginNotFoundException;
import ies.luisvives.serverpeluqueriadam.exceptions.login.LoginsNotFoundException;
import ies.luisvives.serverpeluqueriadam.mapper.LoginMapper;
import ies.luisvives.serverpeluqueriadam.model.Login;
import ies.luisvives.serverpeluqueriadam.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/logins")
public class LoginController {
    private final LoginRepository loginRepository;
    private final LoginMapper loginMapper;

    @Autowired
    public LoginController(LoginRepository loginRepository, LoginMapper loginMapper) {
        this.loginRepository = loginRepository;
        this.loginMapper = loginMapper;
    }

    @CrossOrigin(origins = "http://localhost:3306")
    @GetMapping("/")
    public ResponseEntity<ListLoginPageDTO> findAllLogins(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        try {
            Page<Login>pagedResult = loginRepository.findAll(pageable);
            ListLoginPageDTO listLoginPageDTO = ListLoginPageDTO.builder()
                    .data(loginMapper.toDTO(pagedResult.getContent()))
                    .totalPages(pagedResult.getTotalPages())
                    .totalElements(pagedResult.getTotalElements())
                    .currentPage(pagedResult.getNumber())
                    .build();
            return ResponseEntity.ok(listLoginPageDTO);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Login login = loginRepository.findById(id).orElse(null);
        if (login == null) {
            throw new LoginNotFoundException(id);
        } else {
            return ResponseEntity.ok(loginMapper.toDTO(login));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody LoginDTO loginDTO) {
        try {
            Login login = loginMapper.fromDTO(loginDTO);
            checkLoginData(login);
            Login inserted = loginRepository.save(login);
            return ResponseEntity.ok(loginMapper.toDTO(inserted));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el login. Campos incorrectos.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Login login) {
        try {
            Login updated = loginRepository.findById(id).orElse(null);
            if (updated == null) {
                throw new LoginNotFoundException(id);
            } else {
                checkLoginData(login);
                updated.setInstant(login.getInstant());
                updated.setToken(login.getToken());
                updated.setUser(login.getUser());

                updated = loginRepository.save(updated);
                return ResponseEntity.ok(loginMapper.toDTO(updated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el login. Campos incorrectos.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            Login login = loginRepository.findById(id).orElse(null);
            if (login == null) {
                throw new LoginNotFoundException(id);
            } else {
                loginRepository.delete(login);
                return ResponseEntity.ok(loginMapper.toDTO(login));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el login");
        }
    }

    /**
     * Comprueba los campos obligatorios (requisitos)
     *
     * @param login DAO de Login
     */
    private void checkLoginData(Login login) {
        if (login.getToken() == null || login.getInstant() == null) {
            throw new LoginBadRequestException("Token", "El token es obligatorio");
        }
        if (login.getUser() == null) {
            throw new LoginBadRequestException("User", "El usuario es obligatorio");
        }
    }

//    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> newLogin(@RequestPart("login") LoginDTO loginDTO) {
//        try {
//            Login login = loginMapper.fromDTO(loginDTO);
//            checkLoginData(login);
//            Login inserted = loginRepository.save(login);
//            return ResponseEntity.ok(loginMapper.toDTO(inserted));
//        } catch (LoginNotFoundException ex) {
//            throw new GeneralBadRequestException("Insertar", "Error al insertar el login. Campos incorrectos");
//        }
//    }

}