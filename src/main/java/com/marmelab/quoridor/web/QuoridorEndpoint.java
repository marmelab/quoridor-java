package com.marmelab.quoridor.web;

import com.marmelab.quoridor.game.Game;
import com.marmelab.quoridor.model.Board;
import com.marmelab.quoridor.model.Position;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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

        List<Fence> horizontalFences = new ArrayList<>();
        List<Fence> verticalFences = new ArrayList<>();

        int numberOfIntersections = game.getBoard().getBoardSize() - 1;
        for (int i = 0; i < numberOfIntersections; i++) {
            for (int j = 0; j < numberOfIntersections; j++) {
                horizontalFences.add(new Fence(new Position(i, j), true));
                verticalFences.add(new Fence(new Position(i, j), false));
            }
        }

        model.addAttribute("addVerticalfences", verticalFences);
        model.addAttribute("addHorizontalfences", horizontalFences);
        // return the name of the thymeleaf template
        return "quoridor";
    }

    @PostMapping("add-fence")
    public String addFence(FenceForm fence) {



        return "redirect:/quoridor";
    }

}
