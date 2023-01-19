package pl.markopolo.matchpredictor.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.markopolo.matchpredictor.dto.PredictionRequest;
import pl.markopolo.matchpredictor.models.Response;
import pl.markopolo.matchpredictor.service.PredictionService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("api/v1/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;


    @GetMapping("{userId}/{matchId}")
    @ApiOperation(value = "Get predicted match by id", notes = "Available for EVERYONE\n\n" +
            "Allows to view a match by id.")
    public ResponseEntity<Response> findPredictedMatchById(@PathVariable Long matchId) {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message(String.format("Retrieved predicted match by id: %s", matchId))
                .data(Map.of("match", predictionService.findPredictedMatchById(matchId)))
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Create match prediction", notes = "Available for any USER\n\n" +
            "Allows to register new match prediction in the system. " +
            "The current logged user can create his own prediction")
    public ResponseEntity<Response> createMatch(@Valid @RequestBody PredictionRequest predictionRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/predictions").toUriString());
        return ResponseEntity.created(uri).body(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .message("Created new prediction")
                .data(Map.of("_prediction", predictionService.createPrediction(predictionRequest)))
                .build());
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Update an existing match prediction", notes = "Available for any USER\n\n" +
            "Allows to update an existing match prediction. " +
            "The current logged user can update his own prediction")
    public ResponseEntity<Response> updateMatch(@Valid @RequestBody PredictionRequest predictionRequest) {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Updated match")
                .data(Map.of("prediction", predictionService.updatePrediction(predictionRequest)))
                .build());
    }




}
