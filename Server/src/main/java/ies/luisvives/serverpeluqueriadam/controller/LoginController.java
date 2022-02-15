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
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logins") //TODO : ¿asignamos el API_PATH al principio? - APIConfig.API_PATH
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
    public ResponseEntity<?> findAll(@RequestParam(name = "limit") Optional<String> limit) {
        List<Login> logins = null;
        try {
            logins = loginRepository.findAll();

            if (limit.isPresent() && !logins.isEmpty() && logins.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(loginMapper.toDTO(logins.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!logins.isEmpty()) {
                    return ResponseEntity.ok(loginMapper.toDTO(logins));
                } else {
                    throw new LoginsNotFoundException();
                }
            }
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

    // TODO: ¿Deberíamos de agregar findByUserId para que retorne los logíns del usuario?
//    @GetMapping("/user/{id}")
//    public ResponseEntity<?> findByUserId(@PathVariable String id) {
//        Login login = loginRepository.findById(id).orElse(null);
//        if (login == null) {
//            throw new LoginNotFoundException(id);
//        } else {
//            return ResponseEntity.ok(loginMapper.toDTO(login));
//        }
//    }

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
                updated.setInstance(login.getInstance());
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
    private String image;
    private String token;
    private Date instance;
    private User user;

    private void checkLoginData(Login login) {
        if (login.getToken() == null || login.getInstance() == null) {
            throw new LoginBadRequestException("Token", "El token es obligatorio");
        }
        if (login.getUser() == null) {
            throw new LoginBadRequestException("User", "El usuario es obligatorio");
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> newLogin(@RequestPart("login") LoginDTO loginDTO) {
        try {
            Login login = loginMapper.fromDTO(loginDTO);
            checkLoginData(login);
            Login inserted = loginRepository.save(login);
            return ResponseEntity.ok(loginMapper.toDTO(inserted));
        } catch (LoginNotFoundException ex) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el login. Campos incorrectos");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<Login> pagedResult;
        try {
            pagedResult = loginRepository.findAll(paging);

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
}