package edu.ttap.graphs;

/** An edge of the graph, capturing the source and destination nodes. */
public record Edge(String src, String dest) implements Comparable<Edge>{ 
    @Override
    public boolean equals(Object o) {
        if(o instanceof Edge) {
            Edge e = (Edge)o;
            return e.src().equals(this.src()) && e.dest().equals(this.dest());
        }
        return false;
    }

    public int compareTo(Edge e) {
        if (e.equals(this)) {
            return 0;
        } else {
            String combined = src + dest;
            String eCombined = e.src() + e.dest();
            if(combined.compareTo(eCombined) < 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
