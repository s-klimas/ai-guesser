package pl.sebastianklimas.aiguesser.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response {
    private String message;
    private boolean won;
    private int promptsUsed;
}
