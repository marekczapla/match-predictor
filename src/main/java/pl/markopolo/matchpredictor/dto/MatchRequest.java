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
public class MatchRequest {

    private Long matchId;

    @NotNull(message = "Cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(notes = "Start time of an event (yyyy-MM-dd HH:mm)", example = "2022-12-01 12:00")
    private LocalDateTime startTime;

    @NotBlank(message = "Cannot be empty")
    @Length(min = 2, message = "Min length is 2")
    @ApiModelProperty(notes = "Home team name", example = "Chelsea")
    private String homeTeam;

    @NotBlank(message = "Cannot be empty")
    @Length(min = 2, message = "Min length is 2")
    @ApiModelProperty(notes = "Home team name", example = "Arsenal")
    private String awayTeam;

    @Min(value = 0, message = "Must be min 0")
    @NotNull(message = "Cannot be empty")
    @ApiModelProperty(notes = "Home Team goals", example = "2")
    private int homeGoals;

    @Min(value = 0, message = "Must be min 0")
    @NotNull(message = "Cannot be empty")
    @ApiModelProperty(notes = "Away Team goals", example = "2")
    private int awayGoals;


    @NotBlank(message = "Cannot be empty")
    @Length(min = 2, message = "Min length is 2")
    @ApiModelProperty(notes = "First team that scores goal", example = "Chelsea")
    private String firstTeamToScore;

    @NotBlank(message = "Cannot be empty")
    @Length(min = 2, message = "Min length is 2")
    @ApiModelProperty(notes = "First player that scores goal", example = "Sterling")
    private String firstGoalscorer;


}
