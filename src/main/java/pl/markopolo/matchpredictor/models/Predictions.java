package pl.markopolo.matchpredictor.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_prediction")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Predictions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long predictedMatchId;
    @Column
    @Setter
    private int userHomeGoals;
    @Column
    @Setter
    private int userAwayGoals;
    @Column
    @Setter
    private String userFirstTeamToScore;
    @Column
    @Setter
    private String userFirstGoalscorer;
    @Column
    @Setter
    private int userMatchPoints;
}
