package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Leaf main.java.Node Class: Stores actual values.
 */
public class LeafNode extends Node {
    List<Integer> values; // Values corresponding to the keys
    LeafNode next; // Pointer to the next leaf node (for range queries)
    LeafNode() {
        super(true);
        this.values = new ArrayList<>();
        this.next = null;
    }
}
