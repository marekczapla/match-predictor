package pl.markopolo.matchpredictor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.markopolo.matchpredictor.mapper.MatchMapper;
import pl.markopolo.matchpredictor.dto.MatchRequest;
import pl.markopolo.matchpredictor.exceptions.ResourceNotFoundException;
import pl.markopolo.matchpredictor.models.Match;
import pl.markopolo.matchpredictor.repositories.MatchRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    @Override
    public List<Match> findAllMatches() {
        log.info("Getting list of all matches");
        return null;
    }

    @Override
    public Match findMatchById(Long matchId) {
        log.info("Getting match by id: {}", matchId);
        return matchRepository.findById(matchId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Match with id: %s not found", matchId)));
    }

    @Override
    public Match createMatch(MatchRequest matchRequest) {
        log.info("Creating match");
        Match match = MatchMapper.mapMatchToMatchRequestCreate(matchRequest);
        return matchRepository.save(match);
    }

    @Override
    public Match updateMatch(MatchRequest matchRequest) {
        log.info("Updating match with id: {}", matchRequest.getMatchId());
        if (matchRepository.findById(matchRequest.getMatchId()).isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format("Match with id: %s not found", matchRequest.getMatchId()));
        }
        Match match = MatchMapper.mapMatchRequestToMatchUpdate(matchRequest);
        return matchRepository.save(match);
    }

    @Override
    public boolean deleteMatchById(Long matchId) {
        log.info("Deleting trainer with id: {}", matchId);
        Match match = matchRepository.findById(matchId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Match with id: %s not found", matchId)));

        matchRepository.delete(match);
        return true;
    }
}
