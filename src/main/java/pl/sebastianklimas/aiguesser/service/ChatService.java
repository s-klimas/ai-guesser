package pl.sebastianklimas.aiguesser.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
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

        if (response.isQuestion()) game.incrementQuestions();

        return new Response(response.answer(), response.isGuessed(), game.getQuestions());
    }

    private ChatResponse getResponse(String question) {
        return chatClient.prompt()
                .user(question)
                .system(s -> {
                    s.text("""
                        Take on the role of a player 20 questions.
                        You must answer the question with only one word: YES or NO.
                        You must not reveal the word until the player guesses it.
                        Tell if player guessed it correctly (true or false).
                        Tell if that was a question about the word (true or false).
                        If the number of questions asked exceeds 20, do not allow further guessing and respond only with 'YOU HAVE LOST, START THE GAME AGAIN.'
                        Word to guess: {word}.
                        Current questions count: {count}
                        """);
                    s.param("word", game.getWord());
                    s.param("count", game.getQuestions());
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
