package FibonacciHeap.EC504.CLRS;

/**
 * This class implement the FibonacciHeap Node. Each node x contains a pointer to its parent and a pointer to any one of its children.
 * The children of x are linked together in a circular, doubly linked list.
 * Every node has tht degree property to store the number of children.
 * The boolean-valued attribute x.mark indicates whether node x has lost a child since the last time x was made the child of another node.
 *
 * Created by Fanghz on 3/5/14.
 */
public class FibonacciHeapNode {
    public FibonacciHeapNode p; //the parent of the node
    public FibonacciHeapNode child; // any one of its children
    public boolean mark; // indicates whether node x has lost a child since the last time was made the child of another node
    public int degree; // the the number of children
    public int value; // the value of the node
    public FibonacciHeapNode left; // the left brother of the node
    public FibonacciHeapNode right; // the right brother of the node

    /**
     * Construct a new FibonacciHeap node
     * @param value the value of the new node
     */
    public FibonacciHeapNode(int value) {
        this.value = value;
        this.degree = 0;
        this.p = null;
        this.child = null;
        this.right = this.left = null;
    }

    /**
     * This setP() method set the parent node of the given node
     * @param node the parent node need to be set
     */
    public void setP(FibonacciHeapNode node) {
        this.p = node;
    }

    /**
     * This setChild() method set the child node of the given node
     * @param node the child node need to be set
     */
    public void setChild(FibonacciHeapNode node) {
        this.child = node;
    }

    /**
     * This setMark() method set the mark of the given node
     * @param mark the boolean value of the mark property
     */
    public void setMark(boolean mark) {
        this.mark = mark;
    }

    /**
     * This setDegree() method set the degree of the node
     * @param degree the number of the children of the node
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }

    /**
     * This setValue method set the value of the node
     * @param value the value of the node need to be set
     */
    public void setValue(int value) {
        this.value = value;
    }

    public void setLeft(FibonacciHeapNode left) {
        this.left = left;
    }

    public void setRight(FibonacciHeapNode right) {
        this.right = right;
    }

    /**
     * This getP() method get the parent of the node
     * @return the parent node
     */
    public FibonacciHeapNode getP() {
        return p;
    }

    /**
     * This getChild() method get the child of the node
     * @return the child node
     */
    public FibonacciHeapNode getChild() {
        return child;
    }

    /**
     * This gerMark() method tells us if the node is marked or not
     * @return if the node is marked or not
     */
    public boolean getMark() {
        return mark;
    }

    /**
     * This getDegree() method get the number of the children of the given node
     * @return the degree of the node
     */
    public int getDegree() {
        return degree;
    }

    /**
     * This getValue() method get the value of the node
     * @return the value of the node
     */
    public int getValue() {
        return value;
    }

    public FibonacciHeapNode getLeft() {
        return left;
    }

    public FibonacciHeapNode getRight() {
        return right;
    }
}
