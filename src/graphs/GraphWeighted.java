package graphs;

import java.util.*;


public class GraphWeighted {


    private final Map<Integer, LinkedList> map = new HashMap<>();

    public void addVertex(Integer s) {
        // Add empty linked list to the map
        map.put(s, new LinkedList());
    }

    public void addEdge(Integer source,
                        Integer destination, Boolean bidirectional,
                        int w) {
        // Check if the map contains the source or destination keys
        if (!map.containsKey(source))
            addVertex(source);

        if (!map.containsKey(destination))
            addVertex(destination);

        // Create new node and add it to sources linked list
        map.get(source).add(destination, null, w);

        if (bidirectional) {
            map.get(destination).add(source, null, w);
        }

    }


    public void removeEdge(Integer v) {
        // Delete edges from source and destination linked lists
        LinkedList list = map.get(v);
        if (list.getSize() > 0) {
            Node current = list.getFirst();
            while (current != null) {
                Integer key = current.getElement();
                map.get(key).remove(v);
                current = current.getNext();
            }
            map.remove(v);
        }
    }

    public LinkedList adjacentEdges(Integer v) {
        // Return the edge for a given vertex
        return map.get(v);
    }

    public int getEdgeCount() {
        // Retrieve the total number of edges in the graph
        int edgeCount = 0;
        for (Integer key : map.keySet()) {
            int size = map.get(key).getSize();
            edgeCount += size;
        }
        return edgeCount;
    }


    public Integer getWeight(Integer source, Integer destination) {
        if (!map.containsKey(source))
            throw new NullPointerException("Map does not contain key!");

        //obtain linked list
        LinkedList list = adjacentEdges(source);
        Node current = list.getFirst();

        while (current != null) {
            if (current.getElement().equals(destination)) {
                return current.getWeight();
            }
            current = current.getNext();
        }

        return null;
    }

    public void BFS(Integer v) {
        Queue<Integer> q = new java.util.LinkedList<>();
        List<Integer> labeled = new ArrayList<>();
        q.add(v);
        labeled.add(v);
        Set<Integer> possibleVertices = new HashSet<>();
        while (!q.isEmpty()) {
            Integer vertex = q.remove();
            System.out.print(vertex + " ");
            possibleVertices.add(vertex);
            LinkedList list = adjacentEdges(vertex);

            Node current = list.getFirst();
            while (current != null) {
                Integer n = current.getElement();
                if (!labeled.contains(n)) {
                    q.add(n);
                    labeled.add(n);
                }
                current = current.getNext();
            }
        }

    }


    public static Stack<Integer> dijkstra(GraphWeighted graph, Integer source, Integer target) {
        Set<Integer> q = new HashSet<>();
        Stack<Integer> s = new Stack<>();
        final int INFINITY = Integer.MAX_VALUE;

        HashMap<Integer, Integer> dist = new HashMap<>();
        HashMap<Integer, Integer> prev = new HashMap<>();

        // Set the weights to unvisited nodes to be infinity and previous nodes as null
        for (Integer v : graph.map.keySet()) {
            dist.put(v, INFINITY);
            prev.put(v, null);
            q.add(v);
        }

        // Set the distance from the source to itself as zero
        dist.put(source, 0);
        while (!q.isEmpty()) {
            int min = INFINITY;
            int u = 0;

            // Obtain the minimum distance vertex
            for (Integer vertex : q) {
                if (dist.get(vertex) <= min) {
                    min = dist.get(vertex);
                    u = vertex;
                }
            }

            // Delete u from the Queue
            q.remove(u);

            // Loop over the previous vertices to get shortest path
            if (u == target) {
                int loopCheck = 0;
                if (prev.get(u) != null || u == source) {
                    while (prev.get(u) != null) {

                        // Check for infinite loop
                        if (loopCheck > prev.size()) {
                            return null;
                        }
                        loopCheck++;
                        s.push(u);
                        u = prev.get(u);
                    }

                    return s;
                }
                return null;
            }

            LinkedList list = graph.adjacentEdges(u);
            Node current = list.getFirst();
            // Loop over the adjacent edges and add update shortest path
            while (current != null) {
                // Update the edge count with each new node visited
                Integer r = current.getElement();
                int alt = dist.get(u) + graph.getWeight(u, r);
                if (alt < dist.get(r)) {
                    dist.put(r, alt);
                    prev.put(r, u);
                }
                current = current.getNext();
            }
        }
        return null;
    }


    @Override
    public String toString() {
        // Prints the adjacency list of each vertex.
        StringBuilder builder = new StringBuilder();

        for (Integer v : map.keySet()) {
            builder.append(v.toString()).append(": ");
            LinkedList list = adjacentEdges(v);
            builder.append(list.toString());
            builder.append("\n");
        }

        return (builder.toString());
    }

    public static GraphWeighted makeGraph(int cells) {
        // Object of graph is created.
        GraphWeighted g = new GraphWeighted();

        int[][] grid = new int[cells][cells];
        int count = 0;

        // create a 2D Matrix to store the cell numbers
        for (int i = 0; i < cells; i++) {
            for (int j = 0; j < cells; j++) {
                grid[i][j] = count;
                count++;
            }
        }
        // Iterate ove the 2d array and add the adjacent nodes
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int nextItem = j + 1;

                // Add the next item in the same array
                if (nextItem < grid[i].length) {
                    g.addEdge(grid[i][j], grid[i][nextItem], true, 1);
                }

                int nextCol = i + 1;

                // Add Items from next array
                if (nextCol < grid.length) {
                    g.addEdge(grid[i][j], grid[nextCol][j], true, 1);
                    int prevItem = j - 1;
                    if (prevItem >= 0) {
                        g.addEdge(grid[i][j], grid[nextCol][prevItem], true, 1);
                    }

                    // Add next item in the next array
                    if (nextItem < grid[i].length) {
                        g.addEdge(grid[i][j], grid[nextCol][nextItem], true, 1);
                    }
                }
            }
        }

        return g;

    }

}