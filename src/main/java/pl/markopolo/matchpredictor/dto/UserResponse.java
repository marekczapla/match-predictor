package pl.markopolo.matchpredictor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import pl.markopolo.matchpredictor.models.Role;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserResponse {

    private Long id;
    private String username;
    private List<Role> roles;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private boolean isEnabled;
    private int points;

}