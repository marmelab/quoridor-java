package com.marmelab.quoridor.graph;

import com.marmelab.quoridor.model.Position;
import org.jgrapht.Graph;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

public class GraphFactory {

    private GraphFactory() {
        // Nothing to do
    }

    public static Graph<Position, DefaultEdge> buildGrid(int boardSize) {
        PositionSupplier supplier = new PositionSupplier(boardSize);
        Graph<Position, DefaultEdge> graph = new SimpleGraph<>(supplier, SupplierUtil.createDefaultEdgeSupplier(), false);
        GridGraphGenerator<Position, DefaultEdge> gridGenerator = new GridGraphGenerator<>(boardSize, boardSize);
        gridGenerator.generateGraph(graph);
        return graph;
    }

}
