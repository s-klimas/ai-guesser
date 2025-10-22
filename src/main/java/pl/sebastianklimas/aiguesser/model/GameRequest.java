package pl.sebastianklimas.aiguesser.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GameRequest {
    private String message;
    private Game game;
}
