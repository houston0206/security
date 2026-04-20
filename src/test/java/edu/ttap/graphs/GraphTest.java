package edu.ttap.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Graph class.
 */
public class GraphTest {
    private Graph makeExampleGraph() throws FileNotFoundException {
        File input = new File("data/graph-example.data");
        Scanner file = new Scanner(input);
        List<GraphEntry> edges = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            System.out.println(line);
            String[] tokens = line.split(" ");
            edges.add(new GraphEntry(tokens[0], tokens[1], Integer.parseInt(tokens[2]))); 
        }
        file.close();
        return new Graph(edges);
    }


    /** Contains test */
    @Test
    public void containsTest() throws FileNotFoundException {
        Graph g = makeExampleGraph();
        assertTrue(g.contains("A"));
        assertTrue(g.contains("B"));
        assertTrue(g.contains("C"));
        assertTrue(g.contains("D"));
        assertTrue(g.contains("E"));
        assertTrue(g.contains("F"));
        assertTrue(g.contains("G"));
        assertTrue(g.contains("H"));
        assertTrue(g.contains("I"));
        assertFalse(g.contains("K"));
    }

    /** getWeight test */
    @Test
    public void getWeightTest() throws FileNotFoundException {
        Graph g = makeExampleGraph();
        assertEquals(5, g.getWeight("A", "B").orElse(-1));
        assertEquals(5, g.getWeight("B", "A").orElse(-1));
        assertEquals(3, g.getWeight("A", "D").orElse(-1));
        assertEquals(3, g.getWeight("D", "A").orElse(-1));
        assertEquals(1, g.getWeight("B", "C").orElse(-1));
        assertEquals(1, g.getWeight("C", "B").orElse(-1));
        assertEquals(8, g.getWeight("B", "E").orElse(-1));
        assertEquals(8, g.getWeight("E", "B").orElse(-1));
        assertEquals(4, g.getWeight("C", "E").orElse(-1));
        assertEquals(4, g.getWeight("E", "C").orElse(-1));
        assertEquals(2, g.getWeight("C", "F").orElse(-1));
        assertEquals(2, g.getWeight("F", "C").orElse(-1));
        assertEquals(7, g.getWeight("C", "G").orElse(-1));
        assertEquals(7, g.getWeight("G", "C").orElse(-1));
        assertEquals(11, g.getWeight("D", "G").orElse(-1));
        assertEquals(11, g.getWeight("G", "D").orElse(-1));
        assertEquals(10, g.getWeight("E", "H").orElse(-1));
        assertEquals(10, g.getWeight("H", "E").orElse(-1));
        assertEquals(3, g.getWeight("F", "G").orElse(-1));
        assertEquals(3, g.getWeight("G", "F").orElse(-1));
        assertEquals(8, g.getWeight("F", "H").orElse(-1));
        assertEquals(8, g.getWeight("H", "F").orElse(-1));
        assertEquals(20, g.getWeight("F", "I").orElse(-1));
        assertEquals(20, g.getWeight("I", "F").orElse(-1));
        assertEquals(-1, g.getWeight("A", "C").orElse(-1));
    }

    /** collectDepthFirst test */
    @Test
    public void collectDepthFirstTest() throws FileNotFoundException {
        Graph g = makeExampleGraph();
        List<String> result = g.collectDepthFirst("A");
        assertEquals(9, result.size());
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
        assertTrue(result.contains("C"));
        assertTrue(result.contains("D"));
        assertTrue(result.contains("E"));
        assertTrue(result.contains("F"));
        assertTrue(result.contains("G"));
        assertTrue(result.contains("H"));
        assertTrue(result.contains("I"));
    }

    /** collectBreadthFirst test */
    @Test
    public void collectBreadthFirstTest() throws FileNotFoundException {
        Graph g = makeExampleGraph();
        List<String> result = g.collectBreadthFirst("A");
        assertEquals(9, result.size());
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
        assertTrue(result.contains("C"));
        assertTrue(result.contains("D"));
        assertTrue(result.contains("E"));
        assertTrue(result.contains("F"));
        assertTrue(result.contains("G"));
        assertTrue(result.contains("H"));
        assertTrue(result.contains("I"));
        // N.B., ensure the traversal preserves levels
        assertTrue(result.indexOf("A") < result.indexOf("B"));
        assertTrue(result.indexOf("A") < result.indexOf("D"));
        assertTrue(result.indexOf("B") < result.indexOf("C"));
        assertTrue(result.indexOf("B") < result.indexOf("E"));
        assertTrue(result.indexOf("B") < result.indexOf("G"));
        assertTrue(result.indexOf("D") < result.indexOf("C"));
        assertTrue(result.indexOf("D") < result.indexOf("E"));
        assertTrue(result.indexOf("D") < result.indexOf("G"));
        assertTrue(result.indexOf("C") < result.indexOf("F"));
        assertTrue(result.indexOf("C") < result.indexOf("H"));
        assertTrue(result.indexOf("E") < result.indexOf("F"));
        assertTrue(result.indexOf("E") < result.indexOf("H"));
        assertTrue(result.indexOf("G") < result.indexOf("F"));
        assertTrue(result.indexOf("G") < result.indexOf("H"));
        assertTrue(result.indexOf("F") < result.indexOf("I"));
        assertTrue(result.indexOf("H") < result.indexOf("I"));
    }

    private boolean containsEdge(List<Edge> edges, String src, String dst) {
        return edges.contains(new Edge(src, dst))
            || edges.contains(new Edge(dst, src));
    }

    /** deriveMST */
    @Test
    public void deriveMSTTest() throws FileNotFoundException {
        Graph g = makeExampleGraph();
        List<Edge> mst = g.deriveMST("A");
        assertEquals(8, mst.size());
        assertTrue(containsEdge(mst, "A", "B"));
        assertTrue(containsEdge(mst, "A", "D"));
        assertTrue(containsEdge(mst, "B", "C"));
        assertTrue(containsEdge(mst, "C", "E"));
        assertTrue(containsEdge(mst, "C", "F"));
        assertTrue(containsEdge(mst, "F", "G"));
        assertTrue(containsEdge(mst, "F", "H"));
        assertTrue(containsEdge(mst, "F", "I"));
    }
}
