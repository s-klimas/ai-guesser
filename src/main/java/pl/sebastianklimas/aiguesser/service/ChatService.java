package pl.sebastianklimas.aiguesser.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
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

    public Response guess(String question) {
        ChatResponse response = getResponse(question);

        game.incrementPrompts();

        return new Response(response.answer(), response.won(), game.getPrompts());
    }

    private ChatResponse getResponse(String question) {
        if (game == null || game.getWord().isEmpty() || game.getWord().isBlank()) throw new NoGameStartedException("Tried to guess a word before the game started");

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
                            If asked about anything that isn't related to playing this game answer with 'I can only play 20 questions, so are we playing or not?'
                            If the number of prompts exceeds 20, do not allow further guessing and respond only with 'YOU HAVE LOST, START THE GAME AGAIN.'
                            If the player ask a question that you cannot answer with YES or NO, respond with 'Cannot answer that question, ask YES/NO questions.'
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
}
