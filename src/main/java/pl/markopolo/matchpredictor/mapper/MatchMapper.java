package pl.markopolo.matchpredictor.mapper;

import pl.markopolo.matchpredictor.dto.MatchRequest;
import pl.markopolo.matchpredictor.dto.MatchResponse;
import pl.markopolo.matchpredictor.models.Match;

import java.util.List;
import java.util.stream.Collectors;

public class MatchMapper {

    private static final Long EMPTY_ID = null;

    public static List<MatchResponse> mapMatchListToMatchResponseList(List<Match> matches) {

        return matches.stream().map((match) ->
                        MatchResponse.builder()
                                .startTime(match.getStartTime())
                                .homeTeam(match.getHomeTeam())
                                .awayTeam(match.getAwayTeam())
                                .homeGoals(match.getHomeGoals())
                                .awayGoals(match.getAwayGoals())
                                .startTime(match.getStartTime())
                                .firstTeamToScore(match.getFirstTeamToScore())
                                .firstGoalscorer(match.getFirstGoalscorer())
                                .build())
                .collect(Collectors.toList());
    }

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
