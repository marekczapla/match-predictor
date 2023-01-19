package pl.markopolo.matchpredictor.mapper;

import pl.markopolo.matchpredictor.dto.MatchResponse;
import pl.markopolo.matchpredictor.dto.PredictionRequest;
import pl.markopolo.matchpredictor.models.Prediction;

import java.util.List;
import java.util.stream.Collectors;

public class PredictionMapper {

    private static final Long EMPTY_ID = null;
    PredictionRequest predictionRequest;

    public static List<?> mapPredictionListToPredictionResponseList(List<Prediction> predictions) {

        return predictions.stream().map((prediction) ->
                        MatchResponse.builder()
                                .homeGoals(prediction.getUserHomeGoals())
                                .awayGoals(prediction.getUserAwayGoals())
                                .firstTeamToScore(prediction.getUserFirstTeamToScore())
                                .firstGoalscorer(prediction.getUserFirstGoalscorer())
                                .build())
                .collect(Collectors.toList());
    }

    public static Prediction mapPredictionToPredictionRequestCreate(PredictionRequest predictionRequest) {

        return Prediction.builder()
                .matchId(EMPTY_ID)
                .userHomeGoals(predictionRequest.getUserHomeGoals())
                .userAwayGoals(predictionRequest.getUserAwayGoals())
                .userFirstTeamToScore(predictionRequest.getUserFirstTeamToScore())
                .userFirstGoalscorer(predictionRequest.getUserFirstGoalscorer())
                .build();
    }

    public static Prediction mapPredictionRequestToPredictionUpdate(PredictionRequest predictionRequest) {

        return Prediction.builder()
                .matchId(predictionRequest.getMatchId())
                .userHomeGoals(predictionRequest.getUserHomeGoals())
                .userAwayGoals(predictionRequest.getUserAwayGoals())
                .userFirstTeamToScore(predictionRequest.getUserFirstTeamToScore())
                .userFirstGoalscorer(predictionRequest.getUserFirstGoalscorer())
                .build();
    }
}
