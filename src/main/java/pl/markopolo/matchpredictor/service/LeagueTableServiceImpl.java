package pl.markopolo.matchpredictor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.markopolo.matchpredictor.exceptions.ResourceNotFoundException;
import pl.markopolo.matchpredictor.mapper.LeagueTableMapper;
import pl.markopolo.matchpredictor.models.LeagueTable;
import pl.markopolo.matchpredictor.repositories.LeagueTableRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LeagueTableServiceImpl implements LeagueTableService {

    private final LeagueTableRepository leagueTableRepository;

    @Override
    public LeagueTable showUserPoints(Long userId) {
        log.info("Getting points by userId: {}", userId);
        return leagueTableRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id: %s not found", userId)));

    }

    @Override
    public List<?> showLeagueTable() {
        log.info("Retrieving list of all matches");
        List<LeagueTable> leagueTables = leagueTableRepository.findAll(Sort.by("points").descending());
        return LeagueTableMapper.mapLeagueTableToLeagueTableResponseList(leagueTables);
    }
}
