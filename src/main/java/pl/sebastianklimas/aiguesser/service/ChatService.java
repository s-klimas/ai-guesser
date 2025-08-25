package pl.sebastianklimas.aiguesser.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import pl.sebastianklimas.aiguesser.exceptions.GameAlreadyWonException;
import pl.sebastianklimas.aiguesser.exceptions.GameLostException;
import pl.sebastianklimas.aiguesser.exceptions.IllegalActionException;
import pl.sebastianklimas.aiguesser.exceptions.NoGameStartedException;
import pl.sebastianklimas.aiguesser.model.Game;
import pl.sebastianklimas.aiguesser.model.ChatResponse;
import pl.sebastianklimas.aiguesser.model.Response;

@Service
public class ChatService {
    private final ChatClient chatClient;
    private Game game;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public Response ask(String question) {
        if (game == null || game.getWord().isEmpty() || game.getWord().isBlank()) throw new NoGameStartedException("Tried to guess a word before the game started");
        if (game.isGuessed()) throw new GameAlreadyWonException("Game already won, start new game");
        if (game.getPrompts() > 20) throw new GameLostException("Game lost, try again");

        ChatResponse response = getResponse(question);

        game.incrementPrompts();

        return new Response(response.answer(), response.won(), game.getPrompts());
    }

    private ChatResponse getResponse(String question) {
        return chatClient.prompt()
                .user(u -> {
                    u.text("""
                            Players question: {question}
                            You must answer the player's question with only one word: YES or NO, then assess whether the player guessed the word correctly and won.
                            """);
                    u.param("question", question);
                })
                .system(s -> {
                    s.text("""
                            Take on the role of a player 20 questions, but here will be 20 prompts.
                            You can only talk in the context of the game of 20 questions, and for every question a player asks that does not bring them closer to winning, respond with 'I can only play 20 questions.'
                            If the player ask a question related to game that you cannot answer with YES or NO, respond with 'Cannot answer that question, ask YES/NO questions.'
                            Word to guess: {word}.
                            Current prompts count: {count}
                            """);
                    s.param("word", game.getWord());
                    s.param("count", game.getPrompts());
                })
                .call()
                .entity(ChatResponse.class);
    }

    public Game startGame() {
        String word = chatClient.prompt()
                .user("I want to play 20 questions. Select one noun that I will have to guess. Respond with only one word you chose.")
                .call()
                .content();
        game = new Game(word);
        return game;
    }

    // For testing purposes
    public void setGame(Game game) {
        if (game == null) throw new IllegalActionException("You cannot use it");
        if (game.getWord().split(" ").length > 1) throw new IllegalActionException("You cannot use it");
        this.game = game;
    }
}
