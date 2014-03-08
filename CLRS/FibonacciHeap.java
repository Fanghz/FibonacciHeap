package FibonacciHeap.EC504.CLRS;

import java.util.ArrayList;
import java.util.List;

/**
 * A Fibonacci heap is a collection of rooted trees that are min-heap ordered.
 * That is, each tree obeys the min-heap property: the key of a node is greater than or equal to the key of its parent.
 * Created by Fanghz on 3/5/14.
 */
public class FibonacciHeap {
    FibonacciHeapNode min; // the minimum node in the Fibonacci heap
    int num; // the number of keys stored in the Fibonacci heap

    /**
     * Construct a null Fibonacci heap,
     */
    public FibonacciHeap() {
        this.min = null;
        this.num = 0;
    }

    /**
     * When the insert method performs, initialize properties of the given node, then test if the Fibonacci heap is null.
     * If the Fibonacci heap is null, the node is the only node in H. The node is the H.min.
     * If the Fibonacci heap is not null, the node is inserted into the root list. If necessary, update the H.min.
     * @param H the fibonacci heap
     * @param node the node need to be inserted
     */
    public void FibHeapInsert(FibonacciHeap H, FibonacciHeapNode node) {
        node.setDegree(0);
        node.setP(null);
        node.setChild(null);
        node.setMark(false); // when the node was insert, the mark was false
        if(H.min == null) {
            H.min = node;
        } else {
            fibNodeAdd(node, H.min);
            node.setP(null);
            if(node.getValue() < H.min.getValue()) {
                H.min = node;
            }
        }
        num++;
    }

    public void FibHeapInset(FibonacciHeap H, int value) {
        FibonacciHeapNode node = new FibonacciHeapNode(value);
        FibHeapInsert(H, node);
    }

    /**
     * The union method make two fibonacci heap union. It simply concatenate the two rootlists of H1, H2
     * @param H1
     * @param H2
     * @return
     */
    public FibonacciHeap FibHeapUnion(FibonacciHeap H1, FibonacciHeap H2) {
        FibonacciHeap H = new FibonacciHeap();
        if((H1!= null) && (H2!= null)) {
            H.min = H1.min;
            H.min.setRight(H2.min);
            H2.min.setLeft(H.min);
        }

        if((H1.min == null) ||(H2.min != null && H2.min.getValue() < H1.min.getValue())) {
            H.min = H2.min;
        }
        H.num = H1.num + H2.num;
        return H;
    }

    /**
     * Extract the minimum node from the Fibonacci heap.
     * FIB-HEAP-EXTRACT-MIN works by first making a root out of each of the minimum node’s children and removing the minimum node from the root list.
     * It then consolidates the root list by linking roots of equal degree until at most one root remains of each degree.
     *
     * @param H
     * @return
     */
    public FibonacciHeapNode FibHeapExtractMin(FibonacciHeap H) {
        FibonacciHeapNode z = H.min;
        FibonacciHeapNode x;
        if(z != null) {
            // for each child x of z, add x to the root list of H, and set x.p = null
            while(z.getChild()!= null) {
                x = z.getChild();
                fibNodeRemove(x);
                if(x.getRight() == x) {
                    z.setChild(null);
                } else {
                    z.setChild(x.getRight());
                }
                fibNodeAdd(x, z);
                x.setP(null);
            }

            fibNodeRemove(z);
            if(z.getRight() == z) {
                H.min = null;
            } else {
                H.min = z.getRight();
                FibHeapConsolidate(H);
            }
            H.num = H.num - 1;
        }
        return z;
    }

    public void FibHeapConsolidate(FibonacciHeap H) {
        int D, d;
        FibonacciHeapNode x;
        FibonacciHeapNode w;
        FibonacciHeapNode y;
        FibonacciHeapNode temp;
        D = (int) Math.floor(Math.log(H.num)/ Math.log(2));
        List<FibonacciHeapNode> A = new ArrayList<FibonacciHeapNode>(D);

        for (int i = 0; i < D; i++) {
            A.add(null);
        }

        if(H.min != null) {
            x = H.min.getRight();
            while(x != H.min) {
                w = x;
                d = x.getDegree();
                while (A.get(d)!= null) {
                    y = A.get(d);
                    if(x.getValue() > y.getValue()) {
                        temp = x;
                        x = y;
                        y = temp;
                    }
                    fibHeapLink(H, y, x);
                    A.set(d,null);
                    d++;

                }
                A.set(d, x);
            }
        }
        H.min = null;
        for(int i = 0; i< D; i++) {
            if(A.get(i) != null) {
                if(H.min == null){
                    H.min = A.get(i);
                } else {
                    fibNodeAdd(A.get(i), H.min);
                    if(A.get(i).getValue() < H.min.getValue()) {
                        H.min = A.get(i);
                    }
                }
            }
        }
    }

    /**
     * Descrease the value of a given node
     *
     * Time Complextiy: O(1) amortized cost.
     * @param H the fibonacci heap
     * @param node the node
     * @param k the descreased value
     */
    public void FibHeapDecreaseKey(FibonacciHeap H, FibonacciHeapNode node, int k) {
        FibonacciHeapNode parent;
        if(k < node.getValue()) {
            System.out.println("New key is greater than the current key");
        }
        node.setValue(k);
        parent = node.getP();
        if(parent != null && node.getValue() < parent.getValue()) {
            FibHeapCut(H, node, parent);
            FibHeapCascadingCut(H, parent);
        }
        if(node.getValue() < H.min.getValue()) {
            H.min = node;
        }
    }

    /**
     * Delete a node from a fibonacci heap. Set the node as the Integer.Min_Value, then delete from the fibonacci heap.
     * @param H the fibonacci heap
     * @param node the node need to be deleted.
     */
    public void FibHeapDelete(FibonacciHeap H, FibonacciHeapNode node) {
        FibHeapDecreaseKey(H, node, Integer.MIN_VALUE);
        FibHeapExtractMin(H);
    }

    /**
     * Cut off the link between node and its parent node. Make the node as the root node.
     * @param H the fibonacci heap
     * @param node the given node
     * @param parentNode the parent node of the node
     */
    private void FibHeapCut(FibonacciHeap H, FibonacciHeapNode node, FibonacciHeapNode parentNode ) {
        fibNodeRemove(node);
        fibNodeDecrementDegree(parentNode , node.getDegree());
        if(node == node.getRight()) {
            parentNode.setChild(null);
        } else {
            parentNode.setChild(node.getRight());
        }

        node.setP(null);
        node.setMark(false);
        fibNodeAdd(node, H.min);
    }

    /**
     * Decrement the degree of the given node
     * @param node the given node
     * @param degree the degree need to be decrement
     */
    private void fibNodeDecrementDegree(FibonacciHeapNode node, int degree) {
        node.setDegree(node.getDegree() - degree);
        if(node.getP()!= null) {
            fibNodeDecrementDegree(node.getP(), degree);
        }
    }

    /**
     * Cascading cut the given node
     * @param H
     * @param node
     */
    private void FibHeapCascadingCut(FibonacciHeap H, FibonacciHeapNode node) {
        FibonacciHeapNode parentNode = node.getP();
        if(parentNode != null) {
            if(node.getMark() == false) {
                node.setMark(true);
            } else {
                FibHeapCut(H, node, parentNode);
                FibHeapCascadingCut(H, parentNode);
            }
        }
    }

    /**
     * Delete the node from the doublyLinkedlist
     * Private function
     * @param node the node need to be deleted.
     */
    private void fibNodeRemove(FibonacciHeapNode node) {
        node.getLeft().setRight(node.getRight());
        node.getRight().setLeft(node.getLeft());
    }

    /**
     * Add the new node to before the node
     * @param newNode the node need to be insert
     * @param node the node
     */
    private void fibNodeAdd(FibonacciHeapNode newNode, FibonacciHeapNode node) {
        node.getLeft().setRight(newNode);
        newNode.setLeft(node.getLeft());
        newNode.setRight(node);
        node.setLeft(newNode);
    }

    /**
     * link y to x. Remove y from the root list, make y as the child of x.
     * @param H the fibonacci heap
     * @param y the node need to be removed
     * @param x the node that was added to
     */
    private void fibHeapLink(FibonacciHeap H, FibonacciHeapNode y, FibonacciHeapNode x) {
        fibNodeRemove(y);
        if(x.getChild() == null) {
            x.setChild(y);
        } else {
            fibNodeAdd(y, x.getChild());
        }
        y.setP(x);
        x.setDegree(x.getDegree()+1);
        y.setMark(false);
    }
}
