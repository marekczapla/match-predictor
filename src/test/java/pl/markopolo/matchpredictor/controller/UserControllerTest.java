package pl.markopolo.matchpredictor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.markopolo.matchpredictor.controllers.UserController;
import pl.markopolo.matchpredictor.dto.UserRequest;
import pl.markopolo.matchpredictor.dto.UserResponse;
import pl.markopolo.matchpredictor.exceptions.PermissionDeniedException;
import pl.markopolo.matchpredictor.exceptions.ResourceNotFoundException;
import pl.markopolo.matchpredictor.mapper.UserMapper;
import pl.markopolo.matchpredictor.models.Response;
import pl.markopolo.matchpredictor.models.User;
import pl.markopolo.matchpredictor.security.MyUserDetailsService;
import pl.markopolo.matchpredictor.service.MyUserService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@TestMethodOrder(MethodOrderer.MethodName.class)

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MyUserService myUserService;

    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldGetListOfUsers() throws Exception {

        // given
        User userOne = User.builder()
                .id(1L)
                .username("username_one")
                .password("password_one")
                .roles(null)
                .firstName("firstname_one")
                .lastName("lastname_one")
                .email("one@email.com")
                .isActive(true)
                .build();

        User userTwo = User.builder()
                .id(1L)
                .username("username_two")
                .password("password_two")
                .roles(null)
                .firstName("firstname_two")
                .lastName("lastname_two")
                .email("two@email.com")
                .isActive(true)
                .build();

        List<User> users = List.of(userOne, userTwo);

        given(myUserService.findAllUsers()).willReturn(
                UserMapper.mapUserListToUserResponseList(users));

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Retrieved list of users")
                .data(Map.of("users", myUserService.findAllUsers()))
                .build();

        // when then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void shouldGetUserById_givenValidUserId() throws Exception {

        // given
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .username("username_one")
                .password("password_one")
                .roles(null)
                .firstName("firstname_one")
                .lastName("lastname_one")
                .email("one@email.com")
                .isActive(true)
                .build();

        UserResponse userResponse = UserMapper.mapUserToUserResponse(user);

        given(myUserService.findUserById(userId)).willReturn(userResponse);

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message(String.format("Retrieved user by id: %s", userId))
                .data(Map.of("user", myUserService.findUserById(userId)))
                .build();

        // when then
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void shouldNotGetUserById_whenUserGotNoPermission() throws Exception {

        // given
        Long userId = 1L;

        given(myUserService.findUserById(userId)).willThrow(
                new PermissionDeniedException("Permission denied"));

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Permission denied")
                .build();

        // when then
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void shouldNotGetUserById_givenInvalidUserId() throws Exception {

        // given
        Long userId = 0L;

        given(myUserService.findUserById(userId)).willThrow(
                new ResourceNotFoundException(String.format("User with id: %s not found", userId)));

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(String.format("User with id: %s not found", userId))
                .build();

        // when then
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    void shouldRegisterUser_givenValidAppUserRequest() throws Exception {

        // given
        UserRequest appUserRequest = UserRequest.builder()
                .username("username")
                .password("password")
                .matchingPassword("password")
                .firstName("firstname")
                .lastName("lastname")
                .email("email@email.com")
                .dob(LocalDate.of(2000, 1, 1))
                .build();

        given(myUserService.registerUser(any())).willReturn(true);

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .message("Registered new user")
                .data(Map.of("is_registered", myUserService.registerUser(appUserRequest)))
                .build();

        // when then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    void shouldNotRegisterUser_givenInvalidAppUserRequestUsername() throws Exception {

        // given
        UserRequest appUserRequest = UserRequest.builder()
                .username("u")
                .password("password")
                .matchingPassword("password")
                .firstName("firstname")
                .lastName("lastname")
                .email("email@email.com")
                .dob(LocalDate.of(2000, 1, 1))
                .build();

        Map<String, String> errors = new HashMap<>();
        String fieldName = "username";
        String errorMessage = "Min length is 2";
        errors.put(fieldName, errorMessage);
        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("error occurred")
                .data(Map.of("errors", errors))
                .build();

        // when then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUserRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    void shouldNotRegisterUser_givenAlreadyTakenUsername() throws Exception {

        // given
        UserRequest appUserRequest = UserRequest.builder()
                .username("username")
                .password("password")
                .matchingPassword("password")
                .firstName("firstname")
                .lastName("lastname")
                .email("email@email.com")
                .dob(LocalDate.of(2000, 1, 1))
                .build();

        given(myUserService.registerUser(any())).willThrow(
                new IllegalStateException(String.format("Username %s is already taken", appUserRequest.getUsername())));

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(String.format("Username %s is already taken", appUserRequest.getUsername()))
                .build();

        // when then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUserRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    void shouldNotRegisterUser_givenAlreadyTakenEmail() throws Exception {

        // given
        UserRequest userRequest = UserRequest.builder()
                .username("username")
                .password("password")
                .matchingPassword("password")
                .firstName("firstname")
                .lastName("lastname")
                .email("email@email.com")
                .dob(LocalDate.of(2000, 1, 1))
                .build();

        given(myUserService.registerUser(any())).willThrow(
                new IllegalStateException(String.format("Email %s is already taken", userRequest.getEmail())));

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(String.format("Email %s is already taken", userRequest.getEmail()))
                .build();

        // when then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void shouldUpdateUser_givenValidAppUserRequest() throws Exception {

        // given
        UserRequest userRequest = UserRequest.builder()
                .id(1L)
                .username("username")
                .password("password")
                .matchingPassword("password")
                .firstName("firstname")
                .lastName("lastname")
                .email("email@email.com")
                .dob(LocalDate.of(2000, 1, 1))
                .build();

        given(myUserService.updateUser(any())).willReturn(true);

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Updated user")
                .data(Map.of("is_updated", myUserService.updateUser(userRequest)))
                .build();

        // when then
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void shouldNotUpdateUser_givenUserGotNoPermission() throws Exception {

        // given
        UserRequest userRequest = UserRequest.builder()
                .id(1L)
                .username("username")
                .password("password")
                .matchingPassword("password")
                .firstName("firstname")
                .lastName("lastname")
                .email("email@email.com")
                .dob(LocalDate.of(2000, 1, 1))
                .build();

        given(myUserService.updateUser(any())).willThrow(
                new PermissionDeniedException("Permission denied"));

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Permission denied")
                .build();

        // when then
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void shouldNotUpdateUser_givenAlreadyTakenEmail() throws Exception {

        // given
        UserRequest userRequest = UserRequest.builder()
                .id(1L)
                .username("username")
                .password("password")
                .matchingPassword("password")
                .firstName("firstname")
                .lastName("lastname")
                .email("email@email.com")
                .dob(LocalDate.of(2000, 1, 1))
                .build();

        given(myUserService.updateUser(any())).willThrow(
                new IllegalStateException(String.format("Email %s is already taken", userRequest.getEmail())));

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(String.format("Email %s is already taken", userRequest.getEmail()))
                .build();

        // when then
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void shouldDeleteUserById_givenValidUserId() throws Exception{

        // given
        Long userId = 1L;

        given(myUserService.deleteUserById(userId)).willReturn(true);

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message(String.format("Deleted user with id: %s", userId))
                .data(Map.of("is_deleted", myUserService.deleteUserById(userId)))
                .build();

        // when then
        mockMvc.perform(delete("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void shouldNotDeleteUserById_givenUserGotNoPermission() throws Exception{

        // given
        Long userId = 1L;

        given(myUserService.deleteUserById(userId)).willThrow(
                new PermissionDeniedException("Permission denied"));

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Permission denied")
                .build();

        // when then
        mockMvc.perform(delete("/users/{userId}", userId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void shouldNotDeleteUserById_givenInvalidUserId() throws Exception{

        // given
        Long userId = 0L;

        given(myUserService.deleteUserById(userId)).willThrow(
                new ResourceNotFoundException(String.format("User with id: %s not found", userId)));

        Response expectedResponseBody = Response.builder()
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(String.format("User with id: %s not found", userId))
                .build();

        // when then
        mockMvc.perform(delete("/users/{userId}", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseBody)));
    }
}
