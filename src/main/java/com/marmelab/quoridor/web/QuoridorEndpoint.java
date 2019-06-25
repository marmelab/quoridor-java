package com.marmelab.quoridor.web;

import com.marmelab.quoridor.game.Game;
import com.marmelab.quoridor.model.Pawn;
import com.marmelab.quoridor.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;

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
            return "quoridor";
        }
        if (!gameService.isGameReady()) {
            gameService.joinGame(getSessionId());
        }
        final Pawn myTurn = gameService.isMyTurn(getSessionId());
        final BoardView view = new BoardView(game, myTurn);
        model.addAttribute("squares", view.getSquares());
        model.addAttribute("possibleMoves", view.getPossibleMoves());
        model.addAttribute("pawns", view.getPawns());
        model.addAttribute("verticalFences", view.getVerticalFences());
        model.addAttribute("horizontalFences", view.getHorizontalFences());
        model.addAttribute("addVerticalFences", view.getAddVerticalFences());
        model.addAttribute("addHorizontalFences", view.getAddHorizontalFences());
        if (game.isOver()) {
            model.addAttribute("victory", "Player won");
        } else if (gameService.isGameReady()){
            model.addAttribute("turn", "Turn of ");
            model.addAttribute("player", game.getTurn());
        } else {
            model.addAttribute("waiting", "Waiting for an opponent");
        }
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

    @PostMapping("move-pawn")
    public String movePawn(final MoveForm move) {
        final Game game = gameService.getGame();
        if (game != null) {
            game.movePawn(move.getDirection());
        }
        return "redirect:/quoridor";
    }

    private String getSessionId() {
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }

}
