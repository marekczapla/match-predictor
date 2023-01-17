package pl.markopolo.matchpredictor.mapper;

import pl.markopolo.matchpredictor.dto.MatchRequest;
import pl.markopolo.matchpredictor.models.Match;

public class MatchMapper {

    private static final Long EMPTY_ID = null;

    public static Match mapMatchToMatchRequestCreate(MatchRequest matchRequest) {

        return Match.builder()
                .matchId(EMPTY_ID)
                .homeTeam(matchRequest.getHomeTeam())
                .awayTeam(matchRequest.getAwayTeam())
                .homeGoals(matchRequest.getHomeGoals())
                .awayGoals(matchRequest.getAwayGoals())
                .startTime(matchRequest.getStartTime())
                .build();
    }

    public static Match mapMatchRequestToMatchUpdate(MatchRequest matchRequest) {

        return Match.builder()
                .matchId(matchRequest.getMatchId())
                .homeTeam(matchRequest.getHomeTeam())
                .awayTeam(matchRequest.getAwayTeam())
                .homeGoals(matchRequest.getHomeGoals())
                .awayGoals(matchRequest.getAwayGoals())
                .startTime(matchRequest.getStartTime())
                .firstTeamToScore(matchRequest.getFirstTeamToScore())
                .firstGoalscorer(matchRequest.getFirstGoalscorer())
                .build();
    }
}
