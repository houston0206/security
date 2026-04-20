package edu.ttap.graphs;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

/**
 * A generic, weighted, undirected graph where nodes are represented by strings.
 */
public class Graph {
    Map<Edge, Integer> edges;
    Map<String, ArrayList<String>> vertices;

    /**
     * Constructs a graph from a list of graph entries.
     * @param entries the entries of the graph; each entry is one edge
     */
    public Graph(List<GraphEntry> entries) {
        edges = new HashMap<Edge, Integer>();
        vertices = new HashMap<String, ArrayList<String>>();
        for(GraphEntry i : entries) {
            Edge e = new Edge(i.src(), i.dest());
            edges.put(e, i.weight());
            // arraylists need to be declared, but we also need to add existing values
            ArrayList<String> destConnections = new ArrayList<>();
            destConnections.add(i.src());
            vertices.put(i.dest(), destConnections);
            ArrayList<String> srcConnections = new ArrayList<>();
            srcConnections.add(i.dest());
            vertices.put(i.src(), srcConnections);
        }
    }

    /**
     * @param n the name of the node to check for
     * @return true if the graph contains a node with the given name, false
     * otherwise
     */
    public boolean contains(String n) {
        return vertices.containsKey(n);
    }

    /**
     * @param src the source node
     * @param dest the destination node
     * @return the weight of (src, dest) if it exists, or an empty Optional
     * otherwise
     */
    public Optional<Integer> getWeight(String src, String dest) {
        Optional<Integer> obj = Optional.empty();
        // Note: src and dest are reversible
        Edge e = new Edge(src, dest);
        if (edges.containsKey(e)) {
            obj = Optional.of(edges.get(e));
        }
        return obj;
    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a depth-first traversal
     * beginning at the starting node.
     */
    public List<String> collectDepthFirst(String start) {
        List<String> traversal = new ArrayList<>();
        Stack<String> toVisit = new Stack<>();
        Set<String> visitedNodes = new TreeSet<>();
        toVisit.add(start);
        while(!toVisit.isEmpty()) {
            String curNode = toVisit.pop();
            visitedNodes.add(curNode);
            ArrayList<String> neighbors = vertices.get(curNode);
            for(String i : neighbors) {
                if(!visitedNodes.contains(i)) {
                    toVisit.push(i);
                }
            }
            traversal.add(curNode);
        }
        return traversal;
    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a breadth-first traversal
     * beginning at the starting node.
     */
    public List<String> collectBreadthFirst(String start) {
        // TODO: implement me!
        return null;
    }

    /**
     * Derives a minimum spanning tree of the graph using Prim's algorithm
     * @param start the starting node for this search
     * @return a list of edges that form a minimum spanning tree of the graph
     */
    public List<Edge> deriveMST(String start) {
        // TODO: implement me!
        return null;
    }
}
