package pl.markopolo.matchpredictor.service;

import pl.markopolo.matchpredictor.models.LeagueTable;

import java.util.List;

public interface LeagueTableService {

    List<?> showLeagueTable();

    LeagueTable showUserPoints(Long userId);

}
