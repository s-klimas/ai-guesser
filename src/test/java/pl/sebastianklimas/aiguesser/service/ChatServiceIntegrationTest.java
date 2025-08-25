package pl.sebastianklimas.aiguesser.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sebastianklimas.aiguesser.model.Game;
import pl.sebastianklimas.aiguesser.model.Response;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatServiceIntegrationTest {
    @Autowired
    private ChatService chatService;

    @Test
    void testing_ask_withPromptNotRelatedToGame_shouldReturnProgramedAnswer() {
        Game game = new Game("UMBRELLA");

        Response response = chatService.ask("Is mouse an animal?", game);

        assertEquals("I can only play 20 questions.", response.message(), "Chat should respond with the correct answer.");
    }

    @Test
    void testing_ask_withWrongQuestion_shouldReturnProgramedAnswer() {
        Game game = new Game("UMBRELLA");

        Response response = chatService.ask("What category does this item belong to?", game);

        assertEquals("Cannot answer that question, ask YES/NO questions.", response.message(), "Chat should respond with the correct answer.");
    }

    @Test
    void testing_ask_withCorrectWord_shouldRecognizePlayersVictory() {
        Game game = new Game("UMBRELLA");

        Response response = chatService.ask("Is it umbrella?", game);

        assertTrue(response.won(), "AI should recognize players victory.");
    }

    @Test
    void testing_startGame_shouldReceiveOneWordFromAI() {
        Game game = chatService.startGame();

        assertEquals(1 , game.getWord().split(" ").length, "Chat should respond with only one word.");
    }
}