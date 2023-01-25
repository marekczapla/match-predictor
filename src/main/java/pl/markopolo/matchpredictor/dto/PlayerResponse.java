package pl.markopolo.matchpredictor.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class PlayerResponse {

    private Long matchId;
    private String team;
    private List<String> players;
}
