package com.marmelab.quoridor.web;

import com.marmelab.quoridor.game.Fence;
import com.marmelab.quoridor.model.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddFence {

    private final List<Fence> addHorizontalFences;

    private final List<Fence> addVerticalFences;

    public AddFence(int boardSize, List<Fence> horizontalFences, List<Fence> verticalFences) {
        addHorizontalFences = new ArrayList<>();
        addVerticalFences = new ArrayList<>();
        int numberOfIntersections = boardSize - 1;
        for (int i = 0; i < numberOfIntersections; i++) {
            for (int j = 0; j < numberOfIntersections; j++) {
                addHorizontalFences.add(new Fence(i, j, true));
                addVerticalFences.add(new Fence(i, j, false));
            }
        }

        Set<Fence> removeVerticalFences = new HashSet<>();
        Set<Fence> removeHorizontalFences = new HashSet<>();
        for (Fence fence : horizontalFences) {
            if (addHorizontalFences.contains(fence)) {
                removeHorizontalFences.add(fence);
                final Position rightPosition = new Position(fence.getNorthwestTile());
                rightPosition.translateColumn(-1);
                final Fence rightFence = new Fence(rightPosition, true);
                removeHorizontalFences.add(rightFence);
                final Position leftPosition = new Position(fence.getNorthwestTile());
                leftPosition.translateColumn(1);
                Fence leftFence = new Fence(leftPosition, true);
                removeHorizontalFences.add(leftFence);

                Fence otherFence = new Fence(fence.getNorthwestTile(), false);
                removeVerticalFences.add(otherFence);
            }
        }
        for (Fence fence : verticalFences) {
            if (addVerticalFences.contains(fence)) {
                removeVerticalFences.add(fence);
                final Position upPosition = new Position(fence.getNorthwestTile());
                upPosition.translateRow(-1);
                Fence upFence = new Fence(upPosition, false);
                removeVerticalFences.add(upFence);

                Position downPosition = new Position(fence.getNorthwestTile());
                downPosition.translateRow(1);
                Fence downFence = new Fence(downPosition, false);
                removeVerticalFences.add(downFence);

                Fence otherFence = new Fence(fence.getNorthwestTile(), true);
                removeHorizontalFences.add(otherFence);
            }
        }
        addHorizontalFences.removeAll(removeHorizontalFences);
        addVerticalFences.removeAll(removeVerticalFences);

    }

    public List<Fence> getAddHorizontalFences() {
        return addHorizontalFences;
    }

    public List<Fence> getAddVerticalFences() {
        return addVerticalFences;
    }

}
