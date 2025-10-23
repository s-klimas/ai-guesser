package pl.sebastianklimas.aiguesser.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GameRequest {
    @Schema(name = "message", example = "Is it black?", maxLength = 255, description = "Question to LLM")
    private String message;
    private Game game;
}
