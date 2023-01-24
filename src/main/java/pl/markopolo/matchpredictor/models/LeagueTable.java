package pl.markopolo.matchpredictor.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@SuperBuilder
@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class LeagueTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "points")
    @Setter
    private int points;


    public boolean correctHomeGoals(Prediction prediction, Match match) {

        int predictedHomeGoals = prediction.getUserHomeGoals();
        int matchHomeGoals = match.getHomeGoals();

        if (Objects.equals(predictedHomeGoals, matchHomeGoals)) {
            points += 2;
            return true;
        }
        return false;
    }

    public boolean correctAwayGoals(Prediction prediction, Match match) {

        int predictedAwayGoals = prediction.getUserAwayGoals();
        int matchAwayGoals = match.getAwayGoals();

        if (Objects.equals(predictedAwayGoals, matchAwayGoals)) {
            points += 2;
            return true;
        }
        return false;
    }

    public boolean correctMatchResult(Prediction prediction, Match match) {

        int predictedHomeGoals = prediction.getUserHomeGoals();
        int predictedAwayGoals = prediction.getUserAwayGoals();
        int matchHomeGoals = match.getHomeGoals();
        int matchAwayGoals = match.getAwayGoals();

        if ((predictedHomeGoals > predictedAwayGoals) && (matchHomeGoals > matchAwayGoals) ||
                (predictedHomeGoals < predictedAwayGoals) && (matchHomeGoals < matchAwayGoals) ||
                (predictedHomeGoals == predictedAwayGoals) && (matchHomeGoals == matchAwayGoals))  {
            points += 1;
            return true;
        }
        return false;
    }

    public boolean correctFirstTeamToScore(Prediction prediction, Match match) {
        String predictedFirstTeamToScore =  prediction.getUserFirstTeamToScore();
        String matchFirstTeamToScore = match.getFirstTeamToScore();
        if (Objects.equals(predictedFirstTeamToScore, matchFirstTeamToScore)) {
            points += 1;
            return true;
        }
        return false;
    }

    public boolean correctFirstGoalscorer(Prediction prediction, Match match) {
        String predictedFirstGoalscorer =  prediction.getUserFirstGoalscorer();
        String matchFirstGoalscorer = match.getFirstGoalscorer();
        if (Objects.equals(predictedFirstGoalscorer, matchFirstGoalscorer)) {
            points += 3;
            return true;
        }
        return false;
    }
}
