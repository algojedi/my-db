package test.java;

import static org.junit.jupiter.api.Assertions.*;

import main.java.BPlusTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BPlusTreeTest {
    private BPlusTree tree;

    @BeforeEach
    void setUp() {
        tree = new BPlusTree();
        tree.insert(10, 100);
        tree.insert(20, 200);
        tree.insert(5, 50);
        tree.insert(6, 60);
        tree.insert(12, 120);
        tree.insert(30, 300);
        tree.insert(7, 70);
        tree.insert(17, 170);
    }

    @Test
    void testSearch() {
        assertEquals(60, tree.search(6), "Search for key 6 should return 60");
        assertNull(tree.search(15), "Search for key 15 should return null");
    }

    @Test
    void testInsertAndRetrieve() {
        tree.insert(25, 250);
        assertEquals(250, tree.search(25), "Inserted key 25 should return 250");
    }

    @Test
    void testNonExistingKey() {
        assertNull(tree.search(99), "Search for non-existing key 99 should return null");
    }

    @Test
    void testEdgeCases() {
        tree.insert(Integer.MIN_VALUE, -1);
        tree.insert(Integer.MAX_VALUE, 1);
        assertEquals(-1, tree.search(Integer.MIN_VALUE), "Min integer key should be found");
        assertEquals(1, tree.search(Integer.MAX_VALUE), "Max integer key should be found");
    }
}

