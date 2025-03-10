package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal main.java.Node Class: Used for navigation within the tree.
 */
public class InternalNode extends Node {
    List<Node> children; // Pointers to child nodes
    InternalNode() {
        super(false);
        this.children = new ArrayList<>();
    }
}
