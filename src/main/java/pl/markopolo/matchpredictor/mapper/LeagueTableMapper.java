package pl.markopolo.matchpredictor.mapper;

import pl.markopolo.matchpredictor.dto.LeagueTableRequest;
import pl.markopolo.matchpredictor.dto.LeagueTableResponse;
import pl.markopolo.matchpredictor.dto.MatchResponse;
import pl.markopolo.matchpredictor.models.LeagueTable;
import pl.markopolo.matchpredictor.models.Prediction;

import java.util.List;
import java.util.stream.Collectors;

public class LeagueTableMapper {

    private static final Long EMPTY_ID = null;
    LeagueTableRequest leagueTableRequest;

    public static List<?> mapLeagueTableToLeagueTableResponseList(List<LeagueTable> leagueTables) {

        return leagueTables.stream().map((leagueTable) ->
                        LeagueTableResponse.builder()
                                .userId(leagueTable.getUserId())
                                .points(leagueTable.getPoints())
                                .build())
                .collect(Collectors.toList());
    }


}
