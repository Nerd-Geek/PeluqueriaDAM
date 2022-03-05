package ies.luisvives.serverpeluqueriadam.services.users;

import ies.luisvives.serverpeluqueriadam.dto.user.CreateUserDTO;
import ies.luisvives.serverpeluqueriadam.exceptions.user.NewUserWithDifferentPasswordsException;
import ies.luisvives.serverpeluqueriadam.model.User;
import ies.luisvives.serverpeluqueriadam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsernameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public List<User> findByUsernameContainsIgnoreCase(String username) {
        return userRepository.findByUsernameContainsIgnoreCase(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(String userId) {
        return userRepository.findById(userId);
    }

    public User save(CreateUserDTO newUser) {
        if (newUser.getPassword().contentEquals(newUser.getPasswordConfirm())) {
            User user = User.builder()
                    .name(newUser.getName())
                    .surname(newUser.getSurname())
                    .username(newUser.getUsername())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .email(newUser.getEmail())
                    .phoneNumber(newUser.getPhoneNumber())
                    .image(newUser.getImage())
                    .gender(newUser.getGender())
                    .build();
            try {
                return userRepository.save(user);
            } catch (DataIntegrityViolationException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del usuario ya existe");
            }
        } else {
            throw new NewUserWithDifferentPasswordsException();
        }
    }
}
