package pl.markopolo.matchpredictor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.markopolo.matchpredictor.mapper.UserMapper;
import pl.markopolo.matchpredictor.dto.UserRequest;
import pl.markopolo.matchpredictor.dto.UserResponse;
import pl.markopolo.matchpredictor.exceptions.PermissionDeniedException;
import pl.markopolo.matchpredictor.exceptions.ResourceNotFoundException;
import pl.markopolo.matchpredictor.models.Role;
import pl.markopolo.matchpredictor.models.User;
import pl.markopolo.matchpredictor.repositories.RoleRepository;
import pl.markopolo.matchpredictor.repositories.UserRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MyUserServiceImpl implements MyUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> findAllUsers() {
        log.info("Retrieving list of users");
        List<User> users = userRepository.findAll();
        return UserMapper.mapUserListToUserResponseList(users);
    }

    @Override
    public UserResponse findUserById(Long userId) {
        log.info("Retrieving user with id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id: %s not found", userId)));
        for (Role role : getCurrentUser().getRoles()) {
            if (Objects.equals(role.getName(), "ADMIN") ||
                    Objects.equals(getCurrentUser().getId(), userId)) {
                return UserMapper.mapUserToUserResponse(user);
            }
        }
        throw new PermissionDeniedException("Permission denied");
    }

    @Override
    public User getCurrentUser() {
        Principal principal = SecurityContextHolder
                .getContext()
                .getAuthentication();
        return userRepository.findByUsername(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with username %s not found", principal.getName())));
    }

    @Override
    public boolean registerUser(UserRequest userRequest) {
        log.info("Registering user with username: {}", userRequest.getUsername());
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new IllegalStateException(String.format("Username %s is already taken", userRequest.getUsername()));
        }
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalStateException(String.format("Email %s is already taken", userRequest.getEmail()));
        }

        User user = UserMapper.mapUserRequestToUserCreate(userRequest);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Optional<Role> optionalRole = roleRepository.findByName("USER");
        List<Role> roles = new ArrayList<>();
        optionalRole.ifPresent(roles::add);
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateUser(UserRequest userRequest) {
        log.info("Registering user with username: {}", userRequest.getUsername());
        if (!Objects.equals(userRequest.getId(), getCurrentUser().getId())) {
            throw new PermissionDeniedException("Permission denied");
        }
        Optional<User> optionalUser = userRepository.findByEmail(userRequest.getEmail());
        if (optionalUser.isPresent() &&
                !Objects.equals(userRequest.getId(), optionalUser.get().getId())) {
            throw new IllegalStateException(
                    String.format("Email %s is already taken", userRequest.getEmail()));
        }
        User updatedUser = UserMapper.mapUserRequestToUserUpdate(userRequest);
        updatedUser.setUsername(getCurrentUser().getUsername());
        updatedUser.setActive(getCurrentUser().isActive());
        updatedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        updatedUser.setRoles(getCurrentUser().getRoles());
        userRepository.save(updatedUser);
        return true;
    }

    @Override
    public boolean deleteUserById(Long userId) {
        log.info("Deleting user with id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id: %s not found", userId)));
        for (Role role : getCurrentUser().getRoles()) {
            if (Objects.equals(role.getName(), "ADMIN") ||
                    (Objects.equals(getCurrentUser().getId(), userId))) {
                userRepository.delete(user);
                return true;
            }
        }
        throw new PermissionDeniedException("Permission denied");
    }
}
