package ies.luisvives.serverpeluqueriadam.mapper;

import ies.luisvives.serverpeluqueriadam.dto.user.CreateUserDTO;
import ies.luisvives.serverpeluqueriadam.dto.user.UserDTO;
import ies.luisvives.serverpeluqueriadam.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User fromDTO(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public User fromDTOCreate(CreateUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public List<UserDTO> toDTO(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
