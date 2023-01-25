package pl.markopolo.matchpredictor.service;

import pl.markopolo.matchpredictor.dto.LeagueTableRequest;
import pl.markopolo.matchpredictor.dto.LeagueTableResponse;
import pl.markopolo.matchpredictor.models.LeagueTable;

import java.util.List;

public interface LeagueTableService {

    LeagueTable showMyPoints();
    List<LeagueTableResponse> showLeagueTable();

}
