package com.marmelab.quoridor.web;

import com.marmelab.quoridor.game.Game;
import com.marmelab.quoridor.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("quoridor")
public class QuoridorEndpoint {

    private final GameService gameService;

    public QuoridorEndpoint(final GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public String getGame(final Model model) {
        Game game = gameService.getGame();
        if (game == null) {
            game = gameService.createGame();
        }
        final BoardView view = new BoardView(game);
        model.addAttribute("squares", view.getSquares());
        model.addAttribute("pawn", view.getPawn());
        model.addAttribute("verticalFences", view.getVerticalFences());
        model.addAttribute("horizontalFences", view.getHorizontalFences());
        model.addAttribute("addVerticalFences", view.getAddVerticalFences());
        model.addAttribute("addHorizontalFences", view.getAddHorizontalFences());
        // return the name of the thymeleaf template
        return "quoridor";
    }

    @PostMapping
    public String newGame() {
        gameService.createGame();
        return "redirect:/quoridor";
    }

    @PostMapping("add-fence")
    public String addFence(final FenceForm fence) {
        final Game game = gameService.getGame();
        if (game != null) {
            game.addFence(fence.toFence());
        }
        return "redirect:/quoridor";
    }

}
