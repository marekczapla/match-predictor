package pl.markopolo.matchpredictor.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "match")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    @Column
    private LocalDateTime startTime;
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
    @ManyToOne
    @JoinTable(name = "match_player",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> players;
    @Column
    @Setter
    private String firstTeamToScore;
    @Column
    @Setter
    private String firstGoalscorer;

    public boolean addPlayer(Player player) {
        return players.add(player);
    }

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }
}
