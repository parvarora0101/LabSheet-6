package Graphs;

import java.util.*;

// Graph Class
public class Graph1 {
    private Map<String, List<Edge>> adjList; // Adjacency list using a HashMap

    // Edge class to represent an edge in the graph
    static class Edge {
        String dest;
        int weight;

        public Edge(String dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }

    // Constructor
    public Graph1() {
        adjList = new HashMap<>();
    }

    // Add a vertex
    private void addVertex(String vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    // Add an edge (unweighted)
    public void addEdge(String src, String dest) {
        addVertex(src);
        addVertex(dest);
        adjList.get(src).add(new Edge(dest, 1)); // Unweighted edge (default weight = 1)
        adjList.get(dest).add(new Edge(src, 1)); // For undirected graph
    }

    // Add a weighted edge
    public void addEdge(String src, String dest, int weight) {
        addVertex(src);
        addVertex(dest);
        adjList.get(src).add(new Edge(dest, weight));
        adjList.get(dest).add(new Edge(src, weight)); // For undirected graph
    }

    // BFS Traversal
    public void BFS(String start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Start vertex not found!");
            return;
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            System.out.print(node + " ");

            for (Edge edge : adjList.get(node)) {
                if (!visited.contains(edge.dest)) {
                    visited.add(edge.dest);
                    queue.add(edge.dest);
                }
            }
        }
        System.out.println();
    }

    // Dijkstra's Algorithm (Shortest Path for weighted graphs)
    public void shortestPath(String start, String end) {
        if (!adjList.containsKey(start) || !adjList.containsKey(end)) {
            System.out.println("One or both vertices not found!");
            return;
        }

        Map<String, Integer> dist = new HashMap<>();
        for (String vertex : adjList.keySet()) {
            dist.put(vertex, Integer.MAX_VALUE);
        }
        dist.put(start, 0);

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            String u = edge.dest;

            for (Edge neighbor : adjList.get(u)) {
                String v = neighbor.dest;
                int weight = neighbor.weight;

                if (dist.get(u) + weight < dist.get(v)) {
                    dist.put(v, dist.get(u) + weight);
                    pq.add(new Edge(v, dist.get(v)));
                }
            }
        }

        System.out.println("Shortest path from " + start + " to " + end + ": " + dist.get(end));
    }

    // Main method to test the operations
    public static void main(String[] args) {
        Graph1 graph = new Graph1();

        // Add edges (unweighted)
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");

        System.out.println("BFS Traversal starting from node A:");
        graph.BFS("A");

        // Add weighted edges
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("A", "C", 4);

        System.out.println("\nShortest Path from A to C:");
        graph.shortestPath("A", "C");

        // Add another set of weighted edges with different weights
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("A", "C", 3);

        System.out.println("\nShortest Path from A to C:");
        graph.shortestPath("A", "C");
    }
}
