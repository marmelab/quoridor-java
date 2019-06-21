package com.marmelab.quoridor.web;

import com.marmelab.quoridor.game.Fence;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("quoridor")
public class QuoridorEndpoint {

    private Game game;

    @GetMapping
    public String getGame(final Model model) {
        if (game == null) {
            game = new Game(new Board());
        }
        model.addAttribute("squares", game.getBoard().getNodes());
        model.addAttribute("pawn", game.getPawn());

        final List<Fence> fences = game.getBoard().getFences();

        final List<Fence> verticalFences = fences.stream()
                .filter(fence -> !fence.isHorizontal())
                .collect(Collectors.toList());
        final List<Fence> horizontalFences = fences.stream()
                .filter(Fence::isHorizontal)
                .collect(Collectors.toList());


        final List<Fence> horizontalFencesToAdd = new ArrayList<>();
        final List<Fence> verticalFencesToAdd = new ArrayList<>();
        int numberOfIntersections = game.getBoard().getBoardSize() - 1;
        for (int i = 0; i < numberOfIntersections; i++) {
            for (int j = 0; j < numberOfIntersections; j++) {
                horizontalFencesToAdd.add(new Fence(new Position(i, j), true));
                verticalFencesToAdd.add(new Fence(new Position(i, j), false));
            }
        }
        horizontalFencesToAdd.removeAll(horizontalFences);
        verticalFencesToAdd.removeAll(verticalFences);


        model.addAttribute("addVerticalFences", verticalFencesToAdd);
        model.addAttribute("addHorizontalFences", horizontalFencesToAdd);


        model.addAttribute("verticalFences", verticalFences);
        model.addAttribute("horizontalFences", horizontalFences);

        // return the name of the thymeleaf template
        return "quoridor";
    }

    @PostMapping
    public String newGame() {
        game = new Game(new Board());
        return "redirect:/quoridor";
    }

    @PostMapping("add-fence")
    public String addFence(final FenceForm fence) {
        if (game != null) {
            game.addFence(fence.toFence());
        }
        return "redirect:/quoridor";
    }

}
