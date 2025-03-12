package test.java;


import main.java.PageManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PageManagerTest {
    private static final String TEST_FILE = "test_pages.db";
    private PageManager pageManager;

    @BeforeEach
    void setUp() throws IOException {
        pageManager = new PageManager(TEST_FILE);
    }

    @AfterEach
    void tearDown() throws IOException {
        pageManager.close();
        new File(TEST_FILE).delete(); // Clean up test file
    }

    @Test
    void testWriteAndReadPage() throws IOException {
        byte[] page1 = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] page2 = {9, 10, 11, 12, 13, 14, 15, 16};

        pageManager.writePage(0, page1);
        pageManager.writePage(1, page2);

        assertArrayEquals(page1, pageManager.readPage(0));
        assertArrayEquals(page2, pageManager.readPage(1));
    }

    @Test
    void testReadUnwrittenPage() throws IOException {
        byte[] emptyPage = new byte[8];
        assertArrayEquals(emptyPage, pageManager.readPage(2)); // Unwritten page should be zeros
    }

    @Test
    void testWriteAndOverwritePage() throws IOException {
        byte[] page1 = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] pageNew = {16, 15, 14, 13, 12, 11, 10, 9};

        pageManager.writePage(0, page1);
        pageManager.writePage(0, pageNew); // Overwrite

        assertArrayEquals(pageNew, pageManager.readPage(0));
    }
}
