package pl.markopolo.matchpredictor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.markopolo.matchpredictor.dto.PredictionRequest;
import pl.markopolo.matchpredictor.exceptions.ResourceNotFoundException;
import pl.markopolo.matchpredictor.models.Match;
import pl.markopolo.matchpredictor.models.Prediction;
import pl.markopolo.matchpredictor.repositories.PredictionRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PredictionServiceImpl implements PredictionService {

    private final PredictionRepository predictionRepository;
    @Override
    public List<Prediction> findAllPredictions() {
        return null;
    }

    @Override
    public Match findMatchById(Long matchId) {
        return null;
    }

    @Override
    public Prediction createPrediction(PredictionRequest predictionRequest) {
        return null;
    }

    @Override
    public Prediction updatePrediction(PredictionRequest predictionRequest) {
        return null;
    }

    @Override
    public boolean deletePredictionById(Long matchId) {
        log.info("Deleting trainer with id: {}", matchId);
        Prediction prediction = predictionRepository.findById(matchId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Prediction with id: %s not found", matchId)));

        predictionRepository.delete(prediction);
        return true;
    }
}
