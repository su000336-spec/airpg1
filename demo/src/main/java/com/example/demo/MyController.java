package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyController {

    private static final String SESSION_KEY = "gameState";

    private final GameService gameService;

    public MyController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        GameState state = getOrCreateState(session);
        model.addAttribute("view", gameService.buildView(state));
        return "index";
    }

    @PostMapping("/start")
    public String start(HttpSession session) {
        session.setAttribute(SESSION_KEY, gameService.newGame());
        return "redirect:/";
    }

    @PostMapping("/begin")
    public String begin(@RequestParam String playerName, HttpSession session) {
        GameState state = getOrCreateState(session);
        gameService.startGame(state, playerName);
        session.setAttribute(SESSION_KEY, state);
        return "redirect:/";
    }

    @PostMapping("/choice")
    public String choose(@RequestParam int value, HttpSession session) {
        GameState state = getOrCreateState(session);
        gameService.applyChoice(state, value);
        session.setAttribute(SESSION_KEY, state);
        return "redirect:/";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "Hello! Your web app is working.");
        return "hello";
    }

    private GameState getOrCreateState(HttpSession session) {
        GameState state = (GameState) session.getAttribute(SESSION_KEY);
        if (state == null) {
            state = gameService.newGame();
            session.setAttribute(SESSION_KEY, state);
        }
        return state;
    }
}
