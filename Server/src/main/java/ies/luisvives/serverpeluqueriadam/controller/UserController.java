package ies.luisvives.serverpeluqueriadam.controller;

import ies.luisvives.serverpeluqueriadam.config.APIConfig;
import ies.luisvives.serverpeluqueriadam.config.security.jwt.JwtTokenProvider;
import ies.luisvives.serverpeluqueriadam.config.security.jwt.model.JwtUserResponse;
import ies.luisvives.serverpeluqueriadam.config.security.jwt.model.LoginRequest;
import ies.luisvives.serverpeluqueriadam.dto.user.CreateUserDTO;
import ies.luisvives.serverpeluqueriadam.dto.user.UserDTO;
import ies.luisvives.serverpeluqueriadam.exceptions.GeneralBadRequestException;
import ies.luisvives.serverpeluqueriadam.exceptions.user.UserNotFoundByEmailException;
import ies.luisvives.serverpeluqueriadam.exceptions.user.UserNotFoundByIdException;
import ies.luisvives.serverpeluqueriadam.exceptions.user.UserNotFoundByUsernameException;
import ies.luisvives.serverpeluqueriadam.mapper.UserMapper;
import ies.luisvives.serverpeluqueriadam.model.Login;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.model.UserRole;
import ies.luisvives.serverpeluqueriadam.repository.LoginRepository;
import ies.luisvives.serverpeluqueriadam.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(APIConfig.API_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginRepository loginRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @GetMapping("/")
    public ResponseEntity<?> findAll(
            @RequestParam("searchQuery") Optional<String> searchQuery
    ) {
        List<User> users;
        try {
            if (searchQuery.isPresent())
                users = userService.findByUsernameContainsIgnoreCase(searchQuery.get());
            else
                users = userService.findAll();
            return ResponseEntity.ok(userMapper.toDTO(users));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundByIdException(id);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        User user = userService.findByUsernameIgnoreCase(username).orElse(null);
        if (user == null) {
            throw new UserNotFoundByUsernameException(username);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundByEmailException(email);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    @CrossOrigin(origins = "http://localhost:3306")
    @GetMapping("/me")
    public UserDTO me(@AuthenticationPrincipal User user) {
        return userMapper.toDTO(user);
    }

    @CrossOrigin(origins = "http://localhost:3306")
    @PostMapping("/login")
    public JwtUserResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwtToken = tokenProvider.generateToken(authentication);
        Login login = new Login(jwtToken, Date.from(Instant.now()), user);
        loginRepository.save(login);
        return convertUserEntityAndTokenToJwtUserResponse(user, jwtToken);
    }

    @PostMapping("/")
    public UserDTO nuevoUsuario(@RequestBody CreateUserDTO newUser) {
        return userMapper.toDTO(userService.save(newUser));
    }

    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(User user, String jwtToken) {
        return JwtUserResponse
                .jwtUserResponseBuilder()
                .id(user.getId())
                .name(user.getName())
                .image(user.getImage())
                .userRoles(user.getRoles().stream().map(UserRole::name).collect(Collectors.toSet()))
                .username(user.getUsername())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .gender(user.getGender())
                .token(jwtToken)
                .build();
    }
}