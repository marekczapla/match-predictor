package pl.markopolo.matchpredictor.service;

import pl.markopolo.matchpredictor.dto.MatchRequest;
import pl.markopolo.matchpredictor.dto.MatchResponse;
import pl.markopolo.matchpredictor.models.Match;

import java.util.List;

public interface MatchService {

    List<MatchResponse> findAllMatches();

    Match findMatchById(Long matchId);

    Match createMatch(MatchRequest matchRequest);

    Match updateMatch(MatchRequest matchRequest);

    boolean deleteMatchById(Long matchId);
}
