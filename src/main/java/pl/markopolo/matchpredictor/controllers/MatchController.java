package pl.markopolo.matchpredictor.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.markopolo.matchpredictor.dto.MatchRequest;
import pl.markopolo.matchpredictor.models.Response;
import pl.markopolo.matchpredictor.service.MatchService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("api/v1/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    @ApiOperation(value = "Get a list of all matches", notes = "Available for EVERYONE\n\n" +
            "Allows to view a list of all matches.")
    public ResponseEntity<Response> findAllMatches() {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Retrieved list of matches")
                .data(Map.of("matches", matchService.findAllMatches()))
                .build());
    }

    @GetMapping("{matchId}")
    @ApiOperation(value = "Get match by id", notes = "Available for EVERYONE\n\n" +
            "Allows to view a match by id.")
    public ResponseEntity<Response> findMatchById(@PathVariable Long matchId) {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message(String.format("Retrieved match by id: %s", matchId))
                .data(Map.of("match", matchService.findMatchById(matchId)))
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create match", notes = "Available for ADMIN\n\n" +
            "Allows to register new match in the system.")
    public ResponseEntity<Response> createMatch(@Valid @RequestBody MatchRequest matchRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/matches").toUriString());
        return ResponseEntity.created(uri).body(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .message("Created new match")
                .data(Map.of("match", matchService.createMatch(matchRequest)))
                .build());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update an existing match", notes = "Available for ADMIN\n\n" +
            "Allows to update an existing match.")
    public ResponseEntity<Response> updateMatch(@Valid @RequestBody MatchRequest matchRequest) {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Updated match")
                .data(Map.of("match", matchService.updateMatch(matchRequest)))
                .build());
    }

    @DeleteMapping("{matchId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete an existing match by id", notes = "Available for ADMIN\n\n" +
            "Allows to delete an existing match by id from the system.")
    public ResponseEntity<Response> deleteMatchById(@PathVariable Long matchId) {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message(String.format("Deleted match with id: %s", matchId))
                .data(Map.of("is_deleted", matchService.deleteMatchById(matchId)))
                .build());
    }
}
