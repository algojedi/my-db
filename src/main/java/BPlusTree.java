package main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Implementation of a B+ Tree in Java.
 * Supports insertion and search operations.
 */
public class BPlusTree {
    private static final int ORDER = 3; // Defines the max children per node
    private Node root;

    public BPlusTree() {
        this.root = new LeafNode(); // Start with an empty leaf node as the root
    }
    /**
     * Inserts a key-value pair into the B+ Tree.
     */
    public void insert(int key, int value) {
        LeafNode leaf = findLeafNode(key);
        insertInLeaf(leaf, key, value);
        // If overflow occurs, split the node and adjust the tree structure
        if (leaf.keys.size() > ORDER - 1) {
            splitLeaf(leaf);
        }
    }
    /**
     * Finds the correct leaf node for insertion or search.
     */
    private LeafNode findLeafNode(int key) {
        Node current = root;
        while (!current.isLeaf) {
            InternalNode internal = (InternalNode) current;
            int i = 0;
            while (i < internal.keys.size() && key >= internal.keys.get(i)) {
                i++;
            }
            current = internal.children.get(i);
        }
        return (LeafNode) current;
    }
    /**
     * Inserts a key-value pair into a leaf node.
     */
    private void insertInLeaf(LeafNode leaf, int key, int value) {
        int pos = Collections.binarySearch(leaf.keys, key);
        if (pos >= 0) {
            // If key already exists, update the value
            leaf.values.set(pos, value);
        } else {
            // Otherwise, insert in sorted order
            pos = -pos - 1;
            leaf.keys.add(pos, key);
            leaf.values.add(pos, value);
        }
    }
    /**
     * Splits a full leaf node and propagates changes upwards if necessary.
     */
    private void splitLeaf(LeafNode leaf) {
        LeafNode newLeaf = new LeafNode();
        int midIndex = leaf.keys.size() / 2;
        // Move half of the elements to the new leaf
        newLeaf.keys.addAll(leaf.keys.subList(midIndex, leaf.keys.size()));
        newLeaf.values.addAll(leaf.values.subList(midIndex, leaf.values.size()));
        // Trim the original leaf
        leaf.keys.subList(midIndex, leaf.keys.size()).clear();
        leaf.values.subList(midIndex, leaf.values.size()).clear();
        // Maintain linked list connection
        newLeaf.next = leaf.next;
        leaf.next = newLeaf;
        int newKey = newLeaf.keys.get(0);
        insertIntoParent(leaf, newKey, newLeaf);
    }
    /**
     * Inserts a new key into the parent node and handles internal node splits.
     */
    private void insertIntoParent(Node left, int key, Node right) {
        if (root == left) {
            InternalNode newRoot = new InternalNode();
            newRoot.keys.add(key);
            newRoot.children.add(left);
            newRoot.children.add(right);
            root = newRoot;
            return;
        }
        InternalNode parent = findParent(root, left);
        int pos = Collections.binarySearch(parent.keys, key);
        if (pos < 0) pos = -pos - 1;
        parent.keys.add(pos, key);
        parent.children.add(pos + 1, right);
        if (parent.keys.size() > ORDER - 1) {
            splitInternal(parent);
        }
    }
    /**
     * Splits an internal node when it exceeds the allowed limit.
     */
    private void splitInternal(InternalNode node) {
        InternalNode newInternal = new InternalNode();
        int midIndex = node.keys.size() / 2;
        int upKey = node.keys.get(midIndex);
        // Move keys and children to new internal node
        newInternal.keys.addAll(node.keys.subList(midIndex + 1, node.keys.size()));
        newInternal.children.addAll(node.children.subList(midIndex + 1, node.children.size()));
        node.keys.subList(midIndex, node.keys.size()).clear();
        node.children.subList(midIndex + 1, node.children.size()).clear();
        insertIntoParent(node, upKey, newInternal);
    }
    /**
     * Finds the parent of a given node.
     */
    private InternalNode findParent(Node current, Node child) {
        if (current.isLeaf) return null;
        InternalNode internal = (InternalNode) current;
        for (Node node : internal.children) {
            if (node == child) return internal;
            InternalNode parent = findParent(node, child);
            if (parent != null) return parent;
        }
        return null;
    }
    /**
     * Searches for a key in the B+ Tree and returns the corresponding value.
     */
    public Integer search(int key) {
        LeafNode leaf = findLeafNode(key);
        int pos = Collections.binarySearch(leaf.keys, key);
        return pos >= 0 ? leaf.values.get(pos) : null;
    }
    /**
     * Prints the B+ Tree level-wise.
     */
    public void printTree() {
        List<Node> currentLevel = new ArrayList<>();
        currentLevel.add(root);
        while (!currentLevel.isEmpty()) {
            List<Node> nextLevel = new ArrayList<>();
            for (Node node : currentLevel) {
                System.out.print("[");
                for (int key : node.keys) {
                    System.out.print(key + " ");
                }
                System.out.print("] ");
                if (!node.isLeaf) {
                    nextLevel.addAll(((InternalNode) node).children);
                }
            }
            System.out.println();
            currentLevel = nextLevel;
        }
    }
}
