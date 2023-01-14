package pl.markopolo.matchpredictor.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "match")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Matches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    @Column
    private String homeTeam;
    @Column
    private String awayTeam;
    @Setter
    @Column
    private int homeGoals;
    @Setter
    @Column
    private int awayGoals;
    @Column
    private HashMap<String, String> players;
    @Column
    @Setter
    private String firstTeamToScore;
    @Column
    @Setter
    private String firstGoalscorer;

}
