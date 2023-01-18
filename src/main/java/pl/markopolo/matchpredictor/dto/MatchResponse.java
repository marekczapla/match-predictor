package pl.markopolo.matchpredictor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.markopolo.matchpredictor.models.Player;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MatchResponse {

    private Long matchId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    private String homeTeam;

    private String awayTeam;

    private int homeGoals;

    private int awayGoals;

    private List<PlayerResponse> players;

    private String firstTeamToScore;

    private String firstGoalscorer;


}
