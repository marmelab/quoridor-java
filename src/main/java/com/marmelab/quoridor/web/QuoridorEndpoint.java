package com.marmelab.quoridor.web;

import com.marmelab.quoridor.game.Game;
import com.marmelab.quoridor.model.Board;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("quoridor")
public class QuoridorEndpoint {

    private Game game;

    @GetMapping
    public String getGame(Model model) {
        if (game == null) {
            game = new Game(new Board());
        }
        model.addAttribute("squares", game.getBoard().getNodes());
        model.addAttribute("pawn", game.getPawn());
        // return the name of the thymeleaf template
        return "quoridor";
    }

}
