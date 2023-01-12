package pl.markopolo.matchpredictor;

import pl.markopolo.matchpredictor.dto.UserRequest;
import pl.markopolo.matchpredictor.dto.UserResponse;
import pl.markopolo.matchpredictor.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private static final Long EMPTY_ID = null;

    public static User mapUserRequestToUserCreate(UserRequest userRequest) {

        return User.builder()
                .id(EMPTY_ID)
                .username(userRequest.getUsername())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .dob(userRequest.getDob())
                .build();
    }

    public static User mapUserRequestToUserUpdate(UserRequest userRequest) {

        return User.builder()
                .id(userRequest.getId())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .dob(userRequest.getDob())
                .build();
    }

    public static UserResponse mapUserToUserResponse(User user) {

        return UserResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .dob(user.getDob())
                .build();
    }

    public static List<UserResponse> mapUserListToUserResponseList(List<User> users) {

        return users.stream()
                .map((user) ->
                        UserResponse.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .roles(user.getRoles())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .email(user.getEmail())
                                .dob(user.getDob())
                                .isEnabled(user.isActive())
                                .build())
                .collect(Collectors.toList());
    }

}
