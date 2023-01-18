package pl.markopolo.matchpredictor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PredictionResponse {


}
