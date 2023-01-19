package pl.markopolo.matchpredictor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PredictionResponse {

    private Long matchId;

    private int userHomeGoals;

    private int userAwayGoals;

    private String userFirstTeamToScore;

    private String firstGoalscorer;


}
