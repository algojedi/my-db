package main.java;

import java.util.*;

class BTreeNode {
    List<Integer> keys;
    List<BTreeNode> children;
    List<Integer> values; // Page IDs in leaf nodes
    boolean isLeaf;

    BTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.values = new ArrayList<>();
    }
}

