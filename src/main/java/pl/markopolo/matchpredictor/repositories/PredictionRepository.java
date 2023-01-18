package pl.markopolo.matchpredictor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markopolo.matchpredictor.models.Prediction;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
}
