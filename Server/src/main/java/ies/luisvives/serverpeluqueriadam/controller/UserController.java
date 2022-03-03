package ies.luisvives.serverpeluqueriadam.controller;

import ies.luisvives.serverpeluqueriadam.dto.user.CreateUserDTO;
import ies.luisvives.serverpeluqueriadam.dto.user.UserDTO;
import ies.luisvives.serverpeluqueriadam.exceptions.GeneralBadRequestException;
import ies.luisvives.serverpeluqueriadam.exceptions.user.*;
import ies.luisvives.serverpeluqueriadam.mapper.UserMapper;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository repository;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAll(
            @RequestParam("searchQuery") Optional<String> searchQuery
    ) {
        List<User> users;
        try {
            if (searchQuery.isPresent())
                users = repository.findByUsernameContainsIgnoreCase(searchQuery.get());
            else
                users = repository.findAll();
            return ResponseEntity.ok(userMapper.toDTO(users));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundByIdException(id);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundByUsernameException(username);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }
    @GetMapping("/users/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundByEmailException(email);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> save(@RequestBody CreateUserDTO userDTO) {
        try {
            User user = userMapper.fromDTOCreate(userDTO);
            checkUserData(user);
            User inserted = repository.save(user);
            return ResponseEntity.ok(userMapper.toDTO(inserted));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el usuario. Campos incorrectos.");
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody User newUser, @PathVariable String id) {
        try {
            User userUpdated = repository.findById(id).orElse(null);
        if (userUpdated == null) {
                throw new UserNotFoundByIdException(id);
            } else {
                checkUserData(newUser);
                userUpdated.setName(newUser.getName());
                userUpdated.setImage(newUser.getImage());
                userUpdated.setRoles(newUser.getRoles());
                userUpdated.setUsername(newUser.getUsername());
                userUpdated.setPassword(newUser.getPassword());
                userUpdated.setSurname(newUser.getSurname());
                userUpdated.setPhoneNumber(newUser.getPhoneNumber());
                userUpdated.setEmail(newUser.getEmail());
                userUpdated.setGender(newUser.getGender());
                userUpdated.setLogins(newUser.getLogins());
                userUpdated.setAppointments(newUser.getAppointments());
                userUpdated = repository.save(userUpdated);
                return ResponseEntity.ok(userMapper.toDTO(userUpdated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el usuario. Campos incorrectos.");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable String id) {
        try {
            User user = repository.findById(id).orElse(null);
            if (user == null) {
                throw new UserNotFoundByIdException(id);
            } else {
                repository.delete(user);
                return ResponseEntity.ok(userMapper.toDTO(user));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el usuario");
        }
    }

    private void checkUserData(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new UserBadRequestException("Username", "El username es obligatorio");
        }
        if (user.getPassword() == null) {
            throw new UserBadRequestException("Password", "La password es obligatoria");
        }
    }

}
