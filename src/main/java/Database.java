package main.java;

import java.io.IOException;

class Database {
    BPlusTree index;
    PageManager storage;

    private static int currPageNum = 1;

    void insert(int key, byte[] value) throws IOException {
        int pageId = allocateNewPage();
        storage.writePage(pageId, value);
        index.insert(key, pageId);
    }

    private int allocateNewPage() {
        return currPageNum++;
    }

    byte[] fetch(int key) throws IOException {
        int pageId = index.search(key);
        return storage.readPage(pageId);
    }
}

