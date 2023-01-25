package pl.markopolo.matchpredictor.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

public class LeagueTableRequest {

    private Long userId;

    @Min(value = 0, message = "Must be min 0")
    @ApiModelProperty(notes = "User earned points", example = "12")
    private int points;

}
