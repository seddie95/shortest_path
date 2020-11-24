package graphs;

import java.util.*;


public class GraphWeighted {

    // We use Hashmap to store the edges in the graph
    private Map<Integer, LinkedList> map = new HashMap<>();

    private final int INFINITY = Integer.MAX_VALUE;

    // Integerhis function adds a new vertex to the graph
    public void addVertex(Integer s) {
        map.put(s, new LinkedList());
    }

    // Integerhis function adds the edge
    // between source to destination
    public void addEdge(Integer source,
                        Integer destination, Boolean bidirectional,
                        int w) {

        if (!map.containsKey(source))
            addVertex(source);

        if (!map.containsKey(destination))
            addVertex(destination);


        map.get(source).add(destination, null, w);

        if (bidirectional) {
            map.get(destination).add(source, null, w);
        }

    }

    public LinkedList adjacentEdges(Integer v) {
        return map.get(v);
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


    public void setWeight(Integer source, Integer destination, int weight, Boolean bidirectional) {
        if (!map.containsKey(source))
            throw new NullPointerException("Map does not contain key!");

        // Obtain the linked list for the source node
        LinkedList sourceList = adjacentEdges(source);

        Node sourceCurrent = sourceList.getFirst();

        while (sourceCurrent != null) {
            if (sourceCurrent.getElement().equals(destination)) {
                sourceCurrent.setWeight(weight);
            }
            sourceCurrent = sourceCurrent.getNext();
        }

        if (bidirectional) {
            // Obtain the linked list for the destination node
            LinkedList destinationList = adjacentEdges(destination);
            Node destCurrent = destinationList.getFirst();
            while (destCurrent != null) {
                if (destCurrent.getElement().equals(source)) {
                    destCurrent.setWeight(weight);
                }
                destCurrent = destCurrent.getNext();
            }
        }
    }

    public void setInfinity(Integer source) {
        LinkedList destinationList = adjacentEdges(source);

        Node sourceCurrent = destinationList.getFirst();

        while (sourceCurrent != null) {

            Integer element = sourceCurrent.getElement();
            setWeight(source, element, 10000, true);

            sourceCurrent = sourceCurrent.getNext();
        }


    }

    public void BFS(Integer v) {
        Queue<Integer> q = new java.util.LinkedList<>();
        List<Integer> labeled = new ArrayList<>();
        q.add(v);
        labeled.add(v);
        while (!q.isEmpty()) {
            Integer vertex = q.remove();
            System.out.print(vertex + " ");
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

    public Stack<Integer> dijkstra(Integer source, Integer target) {
        Set<Integer> q = new HashSet<>();
        Stack<Integer> s = new Stack<>();

        Integer[] dist = new Integer[map.size()];
        Integer[] prev = new Integer[map.size()];

        for (Integer v : map.keySet()) {
            dist[v] = INFINITY;
            prev[v] = null;
            q.add(v);
        }
        dist[source] = 0;
        while (!q.isEmpty()) {

            int min = INFINITY;
            int u = 0;

            for (Integer vertex : q) {
                if (dist[vertex] <= min) {
                    min = dist[vertex];
                    u = vertex;
                }
            }

            q.remove(u);

            if (u == target) {
                if (prev[u] != null || u == source) {
                    while (prev[u] != null) {
                        s.push(u);
                        u = prev[u];
                    }
                    return s;
                }
            }
            LinkedList list = adjacentEdges(u);
            Node current = list.getFirst();

            while (current != null) {
                Integer r = current.getElement();
                int alt = dist[u] + getWeight(u, r);
                if (alt < dist[r]) {
                    dist[r] = alt;
                    prev[r] = u;
                }
                current = current.getNext();
            }
        }
        return null;
    }

    // Prints the adjancency list of each vertex.
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Integer v : map.keySet()) {
            builder.append(v.toString()).append(": ");
            LinkedList list = adjacentEdges(v);


            Node current = list.getFirst();

            while (current != null) {
                int element = current.getElement();
                int weight = current.getWeight();
                builder.append("{").append(element).append("=").append(weight).append("} ");

                current = current.getNext();
            }
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

    public static void main(String args[]) {

        GraphWeighted g = GraphWeighted.makeGraph(4);


        g.setInfinity(5);
        System.out.println(g);
        System.out.println(g.dijkstra(0, 9));


    }

}