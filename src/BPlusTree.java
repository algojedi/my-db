import java.util.Collections;

class BPlusTree {
    private BTreeNode root;
    private final int T;

    BPlusTree(int t) { // Minimum degree (T), determines max keys per node
        this.T = t;
        this.root = new BTreeNode(true);
    }

    public void insert(int key, int pageId) {
        BTreeNode r = root;
        if (r.keys.size() == (2 * T - 1)) {
            BTreeNode s = new BTreeNode(false);
            s.children.add(r);
            splitChild(s, 0, r);
            root = s;
        }
        insertNonFull(root, key, pageId);
    }

    private void insertNonFull(BTreeNode node, int key, int pageId) {
        int i = Collections.binarySearch(node.keys, key);
        if (i >= 0) return; // Avoid duplicate keys
        i = -i - 1;

        if (node.isLeaf) {
            node.keys.add(i, key);
            node.values.add(i, pageId);
        } else {
            BTreeNode child = node.children.get(i);
            if (child.keys.size() == (2 * T - 1)) {
                splitChild(node, i, child);
                if (key > node.keys.get(i)) i++;
            }
            insertNonFull(node.children.get(i), key, pageId);
        }
    }

    private void splitChild(BTreeNode parent, int i, BTreeNode child) {
        BTreeNode sibling = new BTreeNode(child.isLeaf);
        parent.keys.add(i, child.keys.remove(T - 1));
        parent.children.add(i + 1, sibling);

        while (child.keys.size() > T - 1) {
            sibling.keys.add(child.keys.remove(T - 1));
        }
        if (!child.isLeaf) {
            while (child.children.size() > T) {
                sibling.children.add(child.children.remove(T));
            }
        } else {
            while (child.values.size() > T - 1) {
                sibling.values.add(child.values.remove(T - 1));
            }
        }
    }

    public Integer search(int key) {
        return search(root, key);
    }

//    private Integer search(BTreeNode node, int key) {
//        int i = Collections.binarySearch(node.keys, key);
//        if (i >= 0) return node.isLeaf ? node.values.get(i) : search(node.children.get(i + 1), key);
//        i = -i - 1;
//        return node.isLeaf ? null : search(node.children.get(i), key);
//    }

    private Integer search(BTreeNode node, int key) {
        int i = Collections.binarySearch(node.keys, key);
        if (i >= 0) {
            if (node.isLeaf) {
                return node.values.get(i);
            } else {
                return search(node.children.get(i), key); // Fix: Use i, not i+1
            }
        }
        i = -i - 1;
        return node.isLeaf ? null : search(node.children.get(i), key);
    }



    @Override
    public String toString() {
        return toString(root, 0);
    }

    private String toString(BTreeNode node, int level) {
        StringBuilder sb = new StringBuilder();
        sb.append("Level ").append(level).append(" Keys: ").append(node.keys).append("\n");
        if (!node.isLeaf) {
            for (BTreeNode child : node.children) {
                sb.append(toString(child, level + 1));
            }
        }
        return sb.toString();
    }
}


