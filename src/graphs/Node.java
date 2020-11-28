package graphs;

public class Node {
    private Integer element;
    private Integer weight;// we assume elements are character strings
    private Node next;

    public Node(Integer s, Node n, Integer w) {
        element = s;
        next = n;
        weight = w;
    }


    public Integer getElement() {
        return element;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }


    public Node getNext() {
        return next;
    }


    public void setElement(Integer newElem) {
        element = newElem;
    }


    public void setNext(Node newNext) {
        next = newNext;
    }

}
