public class BPlusTreeDemo {
    public static void main(String[] args) {
        BPlusTree tree = new BPlusTree(2); // Min degree 2
        tree.insert(10, 1);
        tree.insert(20, 2);
        tree.insert(5, 3);
        tree.insert(6, 4);
        tree.insert(12, 5);

        System.out.println("Page ID for key 10: " + tree.search(10));
        System.out.println("Page ID for key 12: " + tree.search(12));
        System.out.println("Page ID for key 7: " + tree.search(7)); // Should return null
        System.out.println(tree);
    }
}
