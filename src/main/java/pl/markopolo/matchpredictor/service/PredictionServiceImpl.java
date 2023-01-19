package pl.markopolo.matchpredictor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.markopolo.matchpredictor.dto.PredictionRequest;
import pl.markopolo.matchpredictor.exceptions.ResourceNotFoundException;
import pl.markopolo.matchpredictor.mapper.PredictionMapper;
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
    public List<?> findAllPredictions() {
        log.info("Retrieving list of all matches");
        List<Prediction> predictions = predictionRepository.findAll(Sort.by("startTime"));
        return PredictionMapper.mapPredictionListToPredictionResponseList(predictions);
    }

    @Override
    public Prediction findPredictedMatchById(Long matchId) {
        log.info("Getting match by id: {}", matchId);
        return predictionRepository.findById(matchId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Match with id: %s not found", matchId)));
    }

    @Override
    public Prediction createPrediction(PredictionRequest predictionRequest) {
        log.info("Creating prediction");
        Prediction prediction = PredictionMapper.mapPredictionToPredictionRequestCreate(predictionRequest);
        return predictionRepository.save(prediction);
    }

    @Override
    public Prediction updatePrediction(PredictionRequest predictionRequest) {

            log.info("Updating match prediction with id: {}", predictionRequest.getMatchId());
            if (predictionRepository.findById(predictionRequest.getMatchId()).isEmpty()) {
                throw new ResourceNotFoundException(
                        String.format("Match prediction with id: %s not found", predictionRequest.getMatchId()));
            }

            Prediction prediction = PredictionMapper.mapPredictionRequestToPredictionUpdate(predictionRequest);
            return predictionRepository.save(prediction);
    }

    @Override
    public boolean deletePredictionById(Long matchId) {
        log.info("Deleting match prediction with id: {}", matchId);
        Prediction prediction = predictionRepository.findById(matchId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Match prediction with id: %s not found", matchId)));

        predictionRepository.delete(prediction);
        return true;
    }
}
