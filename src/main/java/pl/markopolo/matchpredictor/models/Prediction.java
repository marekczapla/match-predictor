package pl.markopolo.matchpredictor.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_prediction")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    @Column
    private LocalDateTime startTime;
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
