package graphs;

public class LinkedList {
    private Node bottom;
    private int size;


    public LinkedList() {
        bottom = null;
        size = 0;
    }


    public Node add(Integer s, Node n, Integer w) {
        Node new_node = new Node(s, n, w);


        if (bottom == null) {
            bottom = new_node;
            size++;
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

    public void setFirst(Node bottom) {
        this.bottom = bottom;
    }

    public Integer getWeight(Node n) {
        return n.getWeight();
    }

    public void setWeight(Node n, int w) {
        n.setWeight(w);
    }

    public Node remove(Integer e) {

        if (bottom.getElement().equals(e)) {
            bottom = bottom.getNext();
            return bottom;
        }

        Node current = bottom;

        boolean notFound = true;
        while (notFound && current.getNext() != null) {
            if (current.getNext().getElement().equals(e)) {
                notFound = false;
            } else {
                current = current.getNext();
            }
        }
        if (notFound) {
            throw new NullPointerException("The Integer " + e + " was not found in the list!");
        }
        Node temp = current.getNext();
        current.setNext(temp.getNext());
        return bottom;

    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node current = bottom;
        while (current != null) {
            int element = current.getElement();
            int weight = current.getWeight();
            builder.append("{").append(element).append("=").append(weight).append("} ");

            current = current.getNext();
        }
        return builder.toString();
    }


}

