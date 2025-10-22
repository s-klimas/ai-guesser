package pl.sebastianklimas.aiguesser.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatResponse {
    private String answer;
    private boolean won;
}
