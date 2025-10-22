package pl.sebastianklimas.aiguesser.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sebastianklimas.aiguesser.model.Game;
import pl.sebastianklimas.aiguesser.model.GameRequest;
import pl.sebastianklimas.aiguesser.model.Response;
import pl.sebastianklimas.aiguesser.service.ChatService;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

//    @CrossOrigin(origins = "*")
    @PostMapping("/ask")
    public ResponseEntity<Response> chat(@RequestBody GameRequest gr) {
        return ResponseEntity.ok(chatService.ask(gr.getMessage(), gr.getGame()));
    }

//    @CrossOrigin(origins = "*")
    @GetMapping("/new-game")
    public ResponseEntity<Game> startGame() {
        return ResponseEntity.ok(chatService.startGame());
    }
}
