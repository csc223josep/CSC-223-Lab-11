import java.util.*;

/**
 * Class representing a graph and implementing Kruskal's algorithm to find the Minimum Spanning Tree (MST).
 * 
 * Authors: Dan O'Brien, Joseph Kabesha, Isaiah Ayres
 */
public class Graph {
    // Class representing an edge in the graph
    class Edge implements Comparable<Edge> {
        int src, dest, weight;

        // Compare edges based on their weight
        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    }

    // Class representing a subset for the Union-Find algorithm
    class Subset {
        int parent, rank;
    }

    int vertices, edges;
    Edge edge[];

    // Constructor
    Graph(int v, int e) {
        vertices = v;
        edges = e;
        edge = new Edge[edges];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }

    // Find operation for Union-Find with path compression
    int find(Subset subsets[], int i) {
        // Find the subset of an element
        // Apply path compression for optimization
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    // Union operation for Union-Find with union by rank
void union(Subset subsets[], int x, int y) {
    // Find the root of the subset containing element x
    int xroot = find(subsets, x);
    // Find the root of the subset containing element y
    int yroot = find(subsets, y);

    // If the rank of subset rooted at x is less than the rank of subset rooted at y
    if (subsets[xroot].rank < subsets[yroot].rank) {
        // Make xroot the parent of yroot
        subsets[xroot].parent = yroot;
    }
    // If the rank of subset rooted at x is greater than the rank of subset rooted at y
    else if (subsets[xroot].rank > subsets[yroot].rank) {
        // Make yroot the parent of xroot
        subsets[yroot].parent = xroot;
    }
    // If both subsets have the same rank
    else {
        // Arbitrarily choose yroot to be the parent of xroot
        subsets[yroot].parent = xroot;
        // Increment the rank of xroot by one
        subsets[xroot].rank++;
    }
}

    // Kruskal's algorithm to find MST
    void kruskalMST() {
        Edge result[] = new Edge[vertices];
        int e = 0;
        int i = 0;
        for (i = 0; i < vertices; ++i)
            result[i] = new Edge();

        // Sort edges based on weight
        Arrays.sort(edge);

        Subset subsets[] = new Subset[vertices];
        for (i = 0; i < vertices; ++i)
            subsets[i] = new Subset();

        // Initialize subsets
        for (int v = 0; v < vertices; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0;
        // Iterate through all edges
        while (e < vertices - 1) {
            Edge nextEdge = edge[i++];
            int x = find(subsets, nextEdge.src);
            int y = find(subsets, nextEdge.dest);

            // If including this edge does not cause cycle, include it
            if (x != y) {
                result[e++] = nextEdge;
                union(subsets, x, y);
            }
        }

        // Print the edges of MST
        System.out.println("Edges in the MST:");
        int minimumCost = 0;
        for (i = 0; i < e; ++i) {
            System.out.println(result[i].src + " - " + result[i].dest + ": " + result[i].weight);
            minimumCost += result[i].weight;
        }
        System.out.println("Total cost of MST: " + minimumCost);
    }

    public static void main(String[] args) {
        int vertices = 5; // Number of vertices
        int edges = 7; // Number of edges
        Graph graph = new Graph(vertices, edges);

        int[][] edgeInfo = {
            {0, 1, 4},
            {0, 2, 3},
            {1, 2, 1},
            {1, 3, 2},
            {2, 3, 4},
            {3, 4, 2},
            {4, 0, 4}
        };

        // Initialize the edges
        for (int i = 0; i < edges; ++i) {
            graph.edge[i].src = edgeInfo[i][0];
            graph.edge[i].dest = edgeInfo[i][1];
            graph.edge[i].weight = edgeInfo[i][2];
        }

        // Find and print MST
        graph.kruskalMST();
    }
}
