package ies.luisvives.serverpeluqueriadam.controller;

import ies.luisvives.serverpeluqueriadam.dto.UserDTO;
import ies.luisvives.serverpeluqueriadam.mapper.UserMapper;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserRepository repository;
    private final UserMapper userMapper;

    @Autowired
    public UserController (UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(userMapper.toDTO(repository.findAll()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> one(@PathVariable String id) {
        User user = repository.findById(id).orElse(null);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @PostMapping("/users")
    public ResponseEntity<?> newService(@RequestBody UserDTO newUser) {
        User user = userMapper.fromDTO(newUser);
        User userInsert = repository.save(user);
        return ResponseEntity.ok(userMapper.toDTO(userInsert));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> replaceService(@RequestBody User newUser, @PathVariable String id) {
        User userUpdated = repository.findById(id).orElse(null);

        userUpdated.setName(newUser.getName());
        userUpdated.setImage(newUser.getImage());
        userUpdated.setSuperUser(newUser.isSuperUser());
        userUpdated.setUsername(newUser.getUsername());
        userUpdated.setPassword(newUser.getPassword());
        userUpdated.setSurname(newUser.getSurname());
        userUpdated.setPhoneNumber(newUser.getPhoneNumber());
        userUpdated.setEmail(newUser.getEmail());
        userUpdated.setGender(newUser.getGender());
        userUpdated.setLogins(newUser.getLogins());
        userUpdated.setAppointments(newUser.getAppointments());
        return ResponseEntity.ok(userMapper.toDTO(userUpdated));
    }

    @DeleteMapping("/users/{id}")
    public void deleteService(@PathVariable String id) {
        repository.deleteById(id);
    }
}
