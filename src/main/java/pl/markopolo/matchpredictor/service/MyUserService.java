package pl.markopolo.matchpredictor.service;

import pl.markopolo.matchpredictor.dto.UserRequest;
import pl.markopolo.matchpredictor.dto.UserResponse;
import pl.markopolo.matchpredictor.models.User;

import java.util.List;

public interface MyUserService {

    List<UserResponse> findAllUsers();

    UserResponse findUserById(Long userId);

    User getCurrentUser();

    boolean registerUser(UserRequest userRequest);

    boolean updateUser(UserRequest userRequest);

    boolean deleteUserById(Long userId);
}
