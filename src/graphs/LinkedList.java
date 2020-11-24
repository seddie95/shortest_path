package graphs;

public class LinkedList {
    private Node bottom;        // head node of the list
    private long size;        // number of nodes in the list


    public LinkedList() {
        bottom = null;
        size = 0;
    }


    public Node add(Integer s, Node n, Integer w) {
        Node new_node = new Node(s, n, w);


        if (bottom == null) {
            bottom = new_node;
            return new_node;
        }
        Node current = bottom;

        while (current.getNext() != null) {
            current = current.getNext();
        }

        current.setNext(new_node);
        size++;
        return new_node;
    }

    public Node getFirst() {
        return bottom;
    }

    public Integer getWeight(Node n) {
        return n.getWeight();
    }

    public void setWeight(Node n, int w) {
        n.setWeight(w);
    }


    public long getSize() {
        return size;
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        Node a = list.add(3, null, 1);
        Node b = list.add(4, null, 3);
        Node c = list.add(5, null, 6);

    }


}

