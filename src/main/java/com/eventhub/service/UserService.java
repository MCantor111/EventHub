package com.eventhub.service;

import com.eventhub.dto.CreateUserDTO;
import com.eventhub.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(CreateUserDTO dto);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();
}