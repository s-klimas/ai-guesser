package pl.sebastianklimas.aiguesser.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response {
    @Schema(name = "message", example = "YES", maxLength = 255, description = "The answer from chat")
    private String message;
    @Schema(name = "won", example = "false", maxLength = 255, description = "Is the word guessed")
    private boolean won;
    @Schema(name = "promptsUsed", example = "3", maxLength = 255, description = "Amount of question asked")
    private int promptsUsed;
}
