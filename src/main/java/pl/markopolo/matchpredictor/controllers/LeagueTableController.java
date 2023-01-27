package pl.markopolo.matchpredictor.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.markopolo.matchpredictor.models.Response;
import pl.markopolo.matchpredictor.service.LeagueTableService;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/table")
@RequiredArgsConstructor
public class LeagueTableController {

    private final LeagueTableService leagueTableService;

    @GetMapping
    @ApiOperation(value = "Get a League Table", notes = "Available for EVERYONE\n\n" +
            "Allows to view league table.")
    public ResponseEntity<Response> findAllMatches() {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Retrieved league table")
                .data(Map.of("league table", leagueTableService.showLeagueTable()))
                .build());
    }

    @GetMapping("{userId}")
    @ApiOperation(value = "Get user points", notes = "Available for EVERYONE\n\n" +
            "Allows to view a user points by id.")
    public ResponseEntity<Response> findMatchById(@PathVariable Long userId) {
        return ResponseEntity.ok(Response.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message(String.format("Retrieved user points by id: %s", userId))
                .data(Map.of("user points", leagueTableService.showUserPoints(userId)))
                .build());
    }
}
