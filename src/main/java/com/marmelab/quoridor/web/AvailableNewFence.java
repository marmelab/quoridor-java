package com.marmelab.quoridor.web;

import com.marmelab.quoridor.game.Fence;
import com.marmelab.quoridor.model.PositionTile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AvailableNewFence {

    private final List<Fence> addHorizontalFences;

    private final List<Fence> addVerticalFences;

    public AvailableNewFence(final int boardSize, final List<Fence> horizontalFences, final List<Fence> verticalFences) {
        addHorizontalFences = new ArrayList<>();
        addVerticalFences = new ArrayList<>();
        addAllPossibilities(boardSize);
        removeHorizontalFences(horizontalFences);
        removeVerticalFences(verticalFences);
    }

    private void addAllPossibilities(int boardSize) {
        int numberOfIntersections = boardSize - 1;
        for (int i = 0; i < numberOfIntersections; i++) {
            for (int j = 0; j < numberOfIntersections; j++) {
                addHorizontalFences.add(new Fence(i, j, true));
                addVerticalFences.add(new Fence(i, j, false));
            }
        }
    }

    private void removeHorizontalFences(final List<Fence> horizontalFences) {
        Set<Fence> removeVerticalFences = new HashSet<>();
        Set<Fence> removeHorizontalFences = new HashSet<>();
        for (Fence fence : horizontalFences) {
            if (addHorizontalFences.contains(fence)) {
                removeHorizontalFences.add(fence);
                final PositionTile positionTile = new PositionTile(fence.getNorthwestTile());
                final Fence rightFence = new Fence(positionTile.getWestPosition(), true);
                removeHorizontalFences.add(rightFence);
                final Fence leftFence = new Fence(positionTile.getEastPosition(), true);
                removeHorizontalFences.add(leftFence);
                final Fence oppositeFence = new Fence(fence.getNorthwestTile(), false);
                removeVerticalFences.add(oppositeFence);
            }
        }
        addHorizontalFences.removeAll(removeHorizontalFences);
        addVerticalFences.removeAll(removeVerticalFences);
    }

    private void removeVerticalFences(final List<Fence> verticalFences) {
        Set<Fence> removeVerticalFences = new HashSet<>();
        Set<Fence> removeHorizontalFences = new HashSet<>();
        for (Fence fence : verticalFences) {
            if (addVerticalFences.contains(fence)) {
                removeVerticalFences.add(fence);
                final PositionTile positionTile = new PositionTile(fence.getNorthwestTile());
                final Fence upFence = new Fence(positionTile.getNorthPosition(), false);
                removeVerticalFences.add(upFence);
                final Fence downFence = new Fence(positionTile.getSouthPosition(), false);
                removeVerticalFences.add(downFence);
                final Fence oppositeFence = new Fence(fence.getNorthwestTile(), true);
                removeHorizontalFences.add(oppositeFence);
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
