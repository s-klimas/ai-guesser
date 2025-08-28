package pl.sebastianklimas.aiguesser.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sebastianklimas.aiguesser.exceptions.GameAlreadyWonException;
import pl.sebastianklimas.aiguesser.exceptions.GameLostException;
import pl.sebastianklimas.aiguesser.exceptions.NoGameException;
import pl.sebastianklimas.aiguesser.model.Game;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatServiceTest {
    @Autowired
    private ChatService chatService;

    @Test()
    void testing_ask_withMoreThen20Prompts_shouldThrowGameLostException() {
        Game game = new Game("UMBRELLA");
        game.setPrompts(25);

        assertThrows(GameLostException.class, () -> chatService.ask("Is it umbrella?", game), "Game lost, should throw GameLostException.");
    }

    @Test
    void testing_ask_withNoGameStarted_shouldThrowNoGameStartedException() {
        Game game = new Game("UMBRELLA");
        assertThrows(NoGameException.class, () -> chatService.ask("Is it umbrella?", game), "Game not started, should throw NoGameException.");
    }

    @Test
    void testing_ask_withGameWon_shouldThrowGameAlreadyWonException() {
        Game game = new Game("UMBRELLA");
        game.setGuessed(true);

        assertThrows(GameAlreadyWonException.class, () -> chatService.ask("Is it umbrella?", game), "Game already won, should throw GameAlreadyWonException.");
    }
}