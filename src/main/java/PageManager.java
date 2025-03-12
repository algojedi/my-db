package main.java;

import java.io.IOException;
import java.io.RandomAccessFile;

public class PageManager {
    RandomAccessFile file;
    final int PAGE_SIZE = 8;

    public PageManager(String filePath) throws IOException {
        file = new RandomAccessFile(filePath, "rw");
    }

    public void writePage(int id, byte[] data) throws IOException {
        file.seek(id * PAGE_SIZE);
        file.write(data);
    }

    public byte[] readPage(int id) throws IOException {
        file.seek(id * PAGE_SIZE);
        byte[] data = new byte[PAGE_SIZE];
        file.read(data);
        return data;
    }
    // Close the file
    public void close() throws IOException {
        file.close();
    }
}

