package main.java;

public class BPlusTreeDemo {
    public static void main(String[] args) {
        BPlusTree tree = new BPlusTree();
        tree.insert(10, 100);
        tree.insert(20, 200);
        tree.insert(5, 50);
        tree.insert(6, 60);
        tree.insert(12, 120);
        tree.insert(30, 300);
        tree.insert(7, 70);
        tree.insert(17, 170);
        tree.printTree();
        System.out.println("Search 6: " + tree.search(6)); // Expected output: 60
        System.out.println("Search 15: " + tree.search(15)); // Expected output: null
    }

}
