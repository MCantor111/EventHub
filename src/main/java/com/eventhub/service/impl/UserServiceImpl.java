package com.eventhub.service.impl;

import com.eventhub.dto.CreateUserDTO;
import com.eventhub.dto.UserDTO;
import com.eventhub.exception.AuthenticationException;
import com.eventhub.exception.UserNotFoundException;
import com.eventhub.model.Role;
import com.eventhub.model.User;
import com.eventhub.repository.RoleRepository;
import com.eventhub.repository.UserRepository;
import com.eventhub.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createUser(CreateUserDTO dto) {
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new AuthenticationException("A user with that email already exists.");
        }

        Set<Role> resolvedRoles = new LinkedHashSet<>();

        if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByNameIgnoreCase("ROLE_USER")
                    .orElseThrow(() -> new IllegalStateException("Default role ROLE_USER not found."));
            resolvedRoles.add(defaultRole);
        } else {
            for (String roleName : dto.getRoles()) {
                String normalized = normalizeRoleName(roleName);
                Role role = roleRepository.findByNameIgnoreCase(normalized)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + normalized));
                resolvedRoles.add(role);
            }
        }

        User user = new User();
        user.setName(dto.getName().trim());
        user.setEmail(dto.getEmail().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setRoles(resolvedRoles);

        User saved = userRepository.save(user);
        return toDTO(saved);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        return toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getRoles().stream().map(Role::getName).sorted().toList()
        );
    }

    private String normalizeRoleName(String roleName) {
        String trimmed = roleName.trim().toUpperCase();
        return trimmed.startsWith("ROLE_") ? trimmed : "ROLE_" + trimmed;
    }
}