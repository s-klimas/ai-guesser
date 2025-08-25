package pl.sebastianklimas.aiguesser.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sebastianklimas.aiguesser.model.Game;
import pl.sebastianklimas.aiguesser.model.Response;
import pl.sebastianklimas.aiguesser.service.ChatService;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/ask")
    public ResponseEntity<Response> chat(@RequestParam String message) {
        return ResponseEntity.ok(chatService.ask(message));
    }

    @GetMapping("/new-game")
    public ResponseEntity<Game> startGame() {
        return ResponseEntity.ok(chatService.startGame());
    }
}
