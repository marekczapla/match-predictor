package pl.markopolo.matchpredictor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
public class PredictionRequest {

    private Long matchId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Min(value = 0, message = "Must be min 0")
    @NotNull(message = "Cannot be empty")
    @ApiModelProperty(notes = "User prediction of Home Team goals", example = "2")
    private int userHomeGoals;

    @Min(value = 0, message = "Must be min 0")
    @NotNull(message = "Cannot be empty")
    @ApiModelProperty(notes = "User prediction of Away Team goals", example = "2")
    private int userAwayGoals;

    @NotBlank(message = "Cannot be empty")
    @Length(min = 2, message = "Min length is 2")
    @ApiModelProperty(notes = "User prediction team that scores first goal", example = "Chelsea")
    private String userFirstTeamToScore;

    @NotBlank(message = "Cannot be empty")
    @Length(min = 2, message = "Min length is 2")
    @ApiModelProperty(notes = "User prediction of player that scores first goal", example = "Sterling")
    private String userFirstGoalscorer;

}
