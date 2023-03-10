package pl.markopolo.matchpredictor.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.markopolo.matchpredictor.service.MyUserService;
import pl.markopolo.matchpredictor.dto.UserRequest;
import pl.markopolo.matchpredictor.models.Response;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

import static pl.markopolo.matchpredictor.swagger.SwaggerConstants.USERS_API_TAG;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Api(tags = {USERS_API_TAG})
public class UserController {

    private final MyUserService myUserService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Get a list of all users", notes = "Available for ADMIN\n\n" +
            "Allows to view a list of all users registered in the system.")
    public ResponseEntity<Response> findAllUsers() {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Retrieved list of users")
                .data(Map.of("users", myUserService.findAllUsers()))
                .build());
    }

    @GetMapping("{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ApiOperation(value = "Get an user by id", notes = "Available for ADMIN, USER\n\n" +
            "Allows to view an user by id. Every user can be viewed by an admin, but " +
            "the current logged user can only view himself.")
    public ResponseEntity<Response> findUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message(String.format("Retrieved user by id: %s", userId))
                .data(Map.of("user", myUserService.findUserById(userId)))
                .build());
    }

    @PostMapping
    @ApiOperation(value = "Register a new user", notes = "Available for EVERYONE\n\n" +
            "Allows to register an new user in the system. User information must be valid. " +
            "Username and email must be unique. The registered user obtains 'USER' role.")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody UserRequest userRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/users").toUriString());
        return ResponseEntity.created(uri).body(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .message("Registered new user")
                .data(Map.of("is_registered", myUserService.registerUser(userRequest)))
                .build());
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Update an existing user", notes = "Available for USER\n\n" +
            "Allows to update an existing user. The current logged user can update his own account. " +
            "Username cannot be updated and email must remain unique.")
    public ResponseEntity<Response> updateUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Updated user")
                .data(Map.of("is_updated", myUserService.updateUser(userRequest)))
                .build());
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ApiOperation(value = "Delete an existing user by id", notes = "Available for ADMIN, USER\n\n" +
            "Allows to delete an existing user by id from the system. Every user can be deleted by an admin, " +
            "but the current logged user can only delete his own account.")
    public ResponseEntity<Response> deleteUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message(String.format("Deleted user with id: %s", userId))
                .data(Map.of("is_deleted", myUserService.deleteUserById(userId)))
                .build());
    }
}
