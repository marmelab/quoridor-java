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

    private Game game1;

    @GetMapping
    public String getGame(Model model) {
        if (game1 == null) {
            game1 = new Game(new Board());
        }
        model.addAttribute("squares", game1.getBoard().getNodes());
        model.addAttribute("pawn", game1.getPawn());

        return "quoridor";
    }

}
