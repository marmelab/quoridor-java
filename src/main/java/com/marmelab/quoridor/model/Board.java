package com.marmelab.quoridor.model;

import com.marmelab.quoridor.game.Fence;
import com.marmelab.quoridor.graph.GraphFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {

    public static final int DEFAULT_BOARD_SIZE = 9;

    private final int boardSize;

    private Graph<Position, DefaultEdge> graph;

    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    public Board(int boardSize) {
        if (boardSize % 2 == 0) {
            throw new IllegalArgumentException("The size must be an odd number");
        } else if (boardSize < 0) {
            throw new IllegalArgumentException("The size must be positive");
        }
        this.boardSize = boardSize;
        graph = GraphFactory.buildGrid(boardSize);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public List<Position> getNodes() {
        Iterator<Position> iterator = new DepthFirstIterator<>(graph);
        List<Position> squares = new ArrayList<>();
        iterator.forEachRemaining(squares::add);
        return squares;
    }

    public void addFence(final Fence fence) {
        final TileFence tileFence = new TileFence(fence.getNorthwestTile());
        final Position northwestTile = tileFence.getNorthwestTile();
        final Position northeastTile = tileFence.getNortheastTile();
        final Position southwestTile = tileFence.getSouthwestTile();
        final Position southeastTile = tileFence.getSoutheastTile();

        if (fence.isHorizontal()) {
            if (!hasVerticalFence(tileFence)) {
                graph.removeEdge(northwestTile, southwestTile);
                graph.removeEdge(northeastTile, southeastTile);
            }
        } else {
            if (!hasHorizontalFence(tileFence)) {
                graph.removeEdge(northwestTile, northeastTile);
                graph.removeEdge(southwestTile, southeastTile);

            }
        }
    }

    private boolean hasVerticalFence(final TileFence tileFence) {
        final Position northwestTile = tileFence.getNorthwestTile();
        final Position northeastTile = tileFence.getNortheastTile();
        final Position southwestTile = tileFence.getSouthwestTile();
        final Position southeastTile = tileFence.getSoutheastTile();
        return !graph.containsEdge(northwestTile, northeastTile)
                && !graph.containsEdge(southwestTile, southeastTile);
    }

    private boolean hasHorizontalFence(final TileFence tileFence) {
        final Position northwestTile = tileFence.getNorthwestTile();
        final Position northeastTile = tileFence.getNortheastTile();
        final Position southwestTile = tileFence.getSouthwestTile();
        final Position southeastTile = tileFence.getSoutheastTile();
        return !graph.containsEdge(northwestTile, southwestTile)
                && !graph.containsEdge(northeastTile, southeastTile);
    }

    public List<Fence> getFences() {
        List<Fence> fences = new ArrayList<>();
        for (int column = 0; column < boardSize -1; column++) {
            for (int row = 0; row < boardSize -1; row++) {
                final TileFence tileFence = new TileFence(new Position(column, row));
                final Position northwestTile = tileFence.getNorthwestTile();
                if (hasVerticalFence(tileFence)) {
                    final Fence fence = new Fence(northwestTile, false);
                    final Fence upFence = new Fence(fence);
                    upFence.getNorthwestTile().translateRow(-1);
                    if (!fences.contains(upFence))  {
                        fences.add(fence);
                    }
                 } else if (hasHorizontalFence(tileFence)) {
                    final Fence fence = new Fence(northwestTile, true);
                    final Fence leftFence = new Fence(fence);
                    leftFence.getNorthwestTile().translateColumn( -1);
                    if (!fences.contains(leftFence))  {
                        fences.add(fence);
                    }
                }
            }
        }
        return fences;
    }

}
