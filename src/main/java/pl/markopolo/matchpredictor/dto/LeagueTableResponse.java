package pl.markopolo.matchpredictor.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class LeagueTableResponse {

    private Long userId;

    private int points;
}
