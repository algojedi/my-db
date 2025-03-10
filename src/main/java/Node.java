package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a generic node in the B+ Tree.
 */
public abstract class Node {
    List<Integer> keys; // List of keys stored in the node
    boolean isLeaf;
    Node(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
    }
}
