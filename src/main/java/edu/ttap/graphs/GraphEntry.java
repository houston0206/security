package edu.ttap.graphs;

/**
 * An entry in a graph data file, capturing the source node, destination node,
 * and weight of the edge between them.
 */
public record GraphEntry(String src, String dest, int weight) { }
