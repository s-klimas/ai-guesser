package pl.sebastianklimas.aiguesser.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import pl.sebastianklimas.aiguesser.exceptions.GameAlreadyWonException;
import pl.sebastianklimas.aiguesser.exceptions.GameLostException;
import pl.sebastianklimas.aiguesser.exceptions.IllegalActionException;
import pl.sebastianklimas.aiguesser.exceptions.NoGameException;
import pl.sebastianklimas.aiguesser.model.Game;
import pl.sebastianklimas.aiguesser.model.ChatResponse;
import pl.sebastianklimas.aiguesser.model.Response;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public Response ask(String question, Game game) {
        validate(question, game);

        ChatResponse response = getResponse(question, game);

        game.incrementPrompts();

        return new Response(response.getAnswer(), response.isWon(), game.getPrompts());
    }

    private void validate(String question, Game game) {
        if (game == null || game.getWord() == null || game.getWord().isBlank()) throw new NoGameException("Tried to guess a word before the game started");
        if (game.getWord().split(" ").length > 1) throw new IllegalActionException("There should not be any space in the word");
        if (game.isGuessed()) throw new GameAlreadyWonException("Game already won, start new game");
        if (game.getPrompts() > 20) throw new GameLostException("Game lost, try again");
        if (question == null || question.isBlank()) throw new IllegalArgumentException("No question given");
    }

    private ChatResponse getResponse(String question, Game game) {
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
        return new Game(word);
    }
}
