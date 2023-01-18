package pl.markopolo.matchpredictor.service;

import pl.markopolo.matchpredictor.dto.MatchRequest;
import pl.markopolo.matchpredictor.dto.PredictionRequest;
import pl.markopolo.matchpredictor.models.Match;
import pl.markopolo.matchpredictor.models.Prediction;

import java.util.List;

public interface PredictionService {

    List<Prediction> findAllPredictions();

    Match findMatchById(Long matchId);

    Prediction createPrediction(PredictionRequest predictionRequest);

    Prediction updatePrediction(PredictionRequest predictionRequest);

    boolean deletePredictionById(Long matchId);
}
