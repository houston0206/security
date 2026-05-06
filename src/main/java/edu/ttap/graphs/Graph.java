package edu.ttap.graphs;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * A generic, weighted, undirected graph where nodes are represented by strings.
 */
public class Graph {
    Map<Edge, Integer> weights;
    Map<String, ArrayList<String>> vertices;

    /**
     * Constructs a graph from a list of graph entries.
     * @param entries the entries of the graph; each entry is one edge
     */
    public Graph(List<GraphEntry> entries) {
        weights = new HashMap<Edge, Integer>();
        vertices = new HashMap<String, ArrayList<String>>();
        for(GraphEntry i : entries) {
            Edge e = new Edge(i.src(), i.dest());
            weights.put(e, i.weight());
            Edge f = new Edge(i.dest(), i.src());
            weights.put(f, i.weight()); //puts it in twice

            if(!vertices.containsKey(i.src())) {
                vertices.put(i.src(), new ArrayList<String>());
            }
            ArrayList<String> srcConnections = vertices.get(i.src());
            srcConnections.add(i.dest());
            vertices.put(i.src(), srcConnections);
            
            if(!vertices.containsKey(i.dest())) {
                vertices.put(i.dest(), new ArrayList<String>());
            }
            ArrayList<String> destConnections = vertices.get(i.dest());
            destConnections.add(i.src());
            vertices.put(i.dest(), destConnections);
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
        Edge e = new Edge(src, dest);
        if (weights.containsKey(e)) {
            obj = Optional.of(weights.get(e));
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
            if(!visitedNodes.contains(curNode)) {
                visitedNodes.add(curNode);
                ArrayList<String> neighbors = vertices.get(curNode);
                for(String i : neighbors) {
                    toVisit.push(i);
                }
                traversal.add(curNode);
            }
        }
        return traversal;
    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a breadth-first traversal
     * beginning at the starting node.
     */
    public List<String> collectBreadthFirst(String start) {
        List<String> traversal = new ArrayList<>();
        Queue<String> toVisit = new LinkedList<>();
        Set<String> visitedNodes = new TreeSet<>();
        toVisit.add(start);
        while(!toVisit.isEmpty()) {
            String curNode = toVisit.remove();
            if(!visitedNodes.contains(curNode)) {
                visitedNodes.add(curNode);
                ArrayList<String> neighbors = vertices.get(curNode);
                for(String i : neighbors) {
                    toVisit.add(i);
                }
                traversal.add(curNode);
            }
        }
        return traversal;
    }

    /**
     * Derives a minimum spanning tree of the graph using Prim's algorithm
     * @param start the starting node for this search
     * @return a list of edges that form a minimum spanning tree of the graph
     */
    public List<Edge> deriveMST(String start) {
        Set<String> visitedNodes = new TreeSet<>();
        Set<Edge> availableEdges = new TreeSet<>();
        List<Edge> takenEdges = new ArrayList<>();

        visitedNodes.add(start);
        List<String> neighbors = vertices.get(start);
        for (String i : neighbors) {
            Edge e = new Edge(start, i);
            availableEdges.add(e);
        }
        
        while (vertices.size() > visitedNodes.size()) {
            Edge smallest = null;
            for (Edge e : availableEdges) {
                if(smallest == null) {
                    smallest = e;
                } else if (weights.get(e) <= weights.get(smallest)) {
                    smallest = e;
                }
            }

            if (visitedNodes.contains(smallest.dest())) {
                availableEdges.remove(smallest);
                continue;
            } else {
                takenEdges.add(smallest);
                String curNode = smallest.dest();
                visitedNodes.add(curNode);
                
                List<String> adjacents = vertices.get(curNode);
                for (String i : adjacents) {
                    Edge f = new Edge(curNode, i);
                    if (!takenEdges.contains(f)) {
                        availableEdges.add(f);
                    }
                }
            }
        } //end while
        
        return takenEdges;
    } // end MST


    /**
     * 
     * @param file
     * @return a graph
     */
    private static Graph makeGraph(Scanner file) {
        List<GraphEntry> edges = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            String[] tokens = line.split(",");
            edges.add(new GraphEntry(tokens[0], tokens[1], Integer.parseInt(tokens[2]))); 
        }
        file.close();
        return new Graph(edges);
    }

    /**
     * The main method.
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Create graphs
        Scanner[] data = new Scanner[args.length];
        Graph[] configurations = new Graph[args.length];
        for(int i = 0; i < args.length; i++) {
            try {
                data[i] = new Scanner(new File(args[i]));
            } catch (FileNotFoundException e) {
                System.err.println("Could not find file: " + args[i]);
                System.exit(1);
            }
            configurations[i] = makeGraph(data[i]);
            data[i].close();
        }

        Set<String> machines = new TreeSet<>();
        try {
            Scanner mathlan = new Scanner(new File("data/mathlan-machines.txt"));
            while (mathlan.hasNextLine()) {
                String entry = mathlan.nextLine();
                machines.add(entry);
            }
            mathlan.close();
        } catch (FileNotFoundException e) {
            System.err.println("mathlan-machines doesn't exist");
            System.exit(1);
        }
        
        Set<String>[] machinesSets = (Set<String>[])(new Set[args.length]);
        for (int i = 0; i < args.length; i++) {
            machinesSets[i] = new TreeSet(machines);
            Set<String> keys = configurations[i].vertices.keySet();
            String start = keys.iterator().next(); // Get a key from the set to start the traversal with
            List<String> traversal = configurations[i].collectBreadthFirst(start);
            for (String s : traversal) {
                machinesSets[i].remove(s);
            }
            for (String m : machinesSets[i]) {
                System.out.print(m + " ");
            }
            System.out.println("");
        }
    }
}
