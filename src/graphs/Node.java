package graphs;

public class Node {
    private Integer element;
    private Integer weight;// we assume elements are character strings
    private Node next;

    /**
     * Creates a node with the given element and next node.
     */
    public Node(Integer s, Node n, Integer w) {
        element = s;
        next = n;
        weight = w;
    }

    /**
     * Returns the element of this node.
     */
    public Integer getElement() {
        return element;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * Returns the next node of this node.
     */
    public Node getNext() {
        return next;
    }
    // Modifier methods:

    /**
     * Sets the element of this node.
     */
    public void setElement(Integer newElem) {
        element = newElem;
    }

    /**
     * Sets the next node of this node.
     */
    public void setNext(Node newNext) {
        next = newNext;
    }

}
