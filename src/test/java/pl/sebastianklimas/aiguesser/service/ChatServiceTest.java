package pl.sebastianklimas.aiguesser.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sebastianklimas.aiguesser.exceptions.GameAlreadyWonException;
import pl.sebastianklimas.aiguesser.exceptions.GameLostException;
import pl.sebastianklimas.aiguesser.exceptions.NoGameStartedException;
import pl.sebastianklimas.aiguesser.model.Game;
import pl.sebastianklimas.aiguesser.model.Response;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatServiceTest {
    @Autowired
    private ChatService chatService;

    @Test()
    void testing_ask_withMoreThen20Prompts_shouldThrowGameLostException() {
        Game game = new Game("UMBRELLA");
        game.setPrompts(25);
        chatService.setGame(game);

        assertThrows(GameLostException.class, () -> chatService.ask("Is it umbrella?"), "Game lost, should throw GameLostException.");
    }

    @Test
    void testing_ask_withNoGameStarted_shouldThrowNoGameStartedException() {
        assertThrows(NoGameStartedException.class, () -> chatService.ask("Is it umbrella?"), "Game not started, should throw NoGameStartedException.");
    }

    @Test
    void testing_ask_withGameWon_shouldThrowGameAlreadyWonException() {
        Game game = new Game("UMBRELLA");
        game.setGuessed(true);
        chatService.setGame(game);

        assertThrows(GameAlreadyWonException.class, () -> chatService.ask("Is it umbrella?"), "Game already won, should throw GameAlreadyWonException.");
    }
}