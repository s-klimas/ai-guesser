package pl.sebastianklimas.aiguesser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sebastianklimas.aiguesser.model.Game;
import pl.sebastianklimas.aiguesser.model.GameRequest;
import pl.sebastianklimas.aiguesser.model.Response;
import pl.sebastianklimas.aiguesser.service.ChatService;

@RestController
@Tag(name = "Chat controller")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

//    @CrossOrigin(origins = "*")
    @PostMapping("/ask")
    @Operation(summary = "Allows to ask chat a question about a word")
    public ResponseEntity<Response> chat(@RequestBody GameRequest gr) {
        return ResponseEntity.ok(chatService.ask(gr.getMessage(), gr.getGame()));
    }

//    @CrossOrigin(origins = "*")
    @GetMapping("/new-game")
    @Operation(summary = "Returns a word from chat, that player has to guess")
    public ResponseEntity<Game> startGame() {
        return ResponseEntity.ok(chatService.startGame());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Void> handleIllegalStateException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
