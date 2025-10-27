package pl.sebastianklimas.aiguesser.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sebastianklimas.aiguesser.exceptions.GameAlreadyWonException;
import pl.sebastianklimas.aiguesser.exceptions.GameLostException;
import pl.sebastianklimas.aiguesser.exceptions.IllegalActionException;
import pl.sebastianklimas.aiguesser.exceptions.NoGameException;
import pl.sebastianklimas.aiguesser.model.ChatResponse;
import pl.sebastianklimas.aiguesser.model.Game;
import pl.sebastianklimas.aiguesser.model.Response;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ChatServiceTest {

    private ChatClient chatClient;
    private ChatClient.Builder builder;
    private ChatClient.ChatClientRequestSpec prompt;
    private ChatClient.CallResponseSpec callResponse;
    private ChatService chatService;


    @BeforeEach
    void setUp() {
        chatClient = mock(ChatClient.class);
        builder = mock(ChatClient.Builder.class);
        prompt = mock(ChatClient.ChatClientRequestSpec.class);
        callResponse = mock(ChatClient.CallResponseSpec.class);

        when(builder.build()).thenReturn(chatClient);
        when(chatClient.prompt()).thenReturn(prompt);
        when(prompt.user((String) any())).thenReturn(prompt);
        when(prompt.user(ArgumentMatchers.<Consumer<ChatClient.PromptUserSpec>>any())).thenReturn(prompt);
        when(prompt.system((String) any())).thenReturn(prompt);
        when(prompt.system(ArgumentMatchers.<Consumer<ChatClient.PromptSystemSpec>>any())).thenReturn(prompt);
        when(prompt.call()).thenReturn(callResponse);

        chatService = new ChatService(builder);
    }

    @Nested
    @DisplayName("Tests for ask() method")
    class AskTests {

        @Test
        @DisplayName("should return Response and increment prompts when valid input")
        void shouldReturnResponseAndIncrementPrompts() {
            // given
            Game game = new Game("apple");
            ChatResponse chatResponse = new ChatResponse("YES", true);

            when(callResponse.entity(ChatResponse.class)).thenReturn(chatResponse);

            // when
            Response response = chatService.ask("Is it fruit?", game);

            // then
            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isEqualTo("YES");
            assertThat(response.isWon()).isTrue();
            assertThat(response.getPromptsUsed()).isEqualTo(1);
            assertThat(game.getPrompts()).isEqualTo(1);

            verify(chatClient, times(1)).prompt();
            verify(callResponse, times(1)).entity(ChatResponse.class);
        }

        @Test
        @DisplayName("should throw NoGameException when game is null")
        void shouldThrowWhenGameNull() {
            assertThatThrownBy(() -> chatService.ask("Is it big?", null))
                    .isInstanceOf(NoGameException.class)
                    .hasMessageContaining("before the game started");
        }

        @Test
        @DisplayName("should throw NoGameException when word is null or blank")
        void shouldThrowWhenWordNullOrBlank() {
            Game game1 = new Game(null);
            Game game2 = new Game(" ");

            assertThatThrownBy(() -> chatService.ask("?", game1))
                    .isInstanceOf(NoGameException.class);
            assertThatThrownBy(() -> chatService.ask("?", game2))
                    .isInstanceOf(NoGameException.class);
        }

        @Test
        @DisplayName("should throw IllegalActionException when word has spaces")
        void shouldThrowWhenWordHasSpaces() {
            Game game = new Game("two words");
            assertThatThrownBy(() -> chatService.ask("?", game))
                    .isInstanceOf(IllegalActionException.class);
        }

        @Test
        @DisplayName("should throw GameAlreadyWonException when game is guessed")
        void shouldThrowWhenGameAlreadyWon() {
            Game game = new Game("apple");
            game.setGuessed(true);

            assertThatThrownBy(() -> chatService.ask("?", game))
                    .isInstanceOf(GameAlreadyWonException.class);
        }

        @Test
        @DisplayName("should throw GameLostException when prompts exceed 20")
        void shouldThrowWhenTooManyPrompts() {
            Game game = new Game("apple");
            game.setPrompts(21);

            assertThatThrownBy(() -> chatService.ask("?", game))
                    .isInstanceOf(GameLostException.class);
        }

        @Test
        @DisplayName("should throw IllegalArgumentException when question is blank")
        void shouldThrowWhenQuestionBlank() {
            Game game = new Game("apple");

            assertThatThrownBy(() -> chatService.ask(" ", game))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Tests for startGame() method")
    class StartGameTests {

        @Test
        @DisplayName("should start a new game with a valid word")
        void shouldStartGameSuccessfully() {
            when(callResponse.content()).thenReturn("banana");

            Game game = chatService.startGame();

            assertThat(game).isNotNull();
            assertThat(game.getWord()).isEqualTo("banana");
            verify(chatClient, times(1)).prompt();
        }

        @Test
        @DisplayName("should throw exception when chatClient returns blank word")
        void shouldThrowWhenBlankWordReturned() {
            when(callResponse.content()).thenReturn(" ");

            assertThatThrownBy(() -> chatService.startGame())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("No word given");
        }
    }
}