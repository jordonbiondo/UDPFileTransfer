package org.DataCom.Server;

import java.util.*;
import java.io.*;
import java.net.*;


public class UFTFileSplitter {

    /*
     * Default size, in bytes, for each chunk
     */
    public static final int DEFAULT_CHUNK_SIZE = 1024;


    /*
     * File to being split.
     */
    private File file;


    /*
     * Maximum number of bytes per chunk;
     */
    private int maxChunkSize = DEFAULT_CHUNK_SIZE;


    /*
     * List of byte arrays representing the given file.
     */
    private ArrayList<byte[]> chunks;


    Buffer == new Buffer();

    

    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////

    /*
     * Create a new splitter for a given file
     *
     * Main Constructor to be filled in
     */
    public UFTFileSplitter(File file, int maxChunkSize) {

    }


    /*
     * Create a new splitter from a filename
     */
    public UFTFileSplitter(String filename, int maxChunkSize) throws FileNotFoundException {
	this(new File(filename), maxChunkSize);
    }


    /*
     * Create a new splitter for a given file
     */
    public UFTFileSplitter(File file) {
	this(file, DEFAULT_CHUNK_SIZE);
    }


    /*
     * Create a new splitter from a filename
     */
    public UFTFileSplitter(String filename) throws FileNotFoundException {
	this(new File(filename));
    }


    /*
     * Chunk up dat file. mkay
     */
    private void chunk() {

    }


    /*
     * Rechunk the file with a given chunk size.
     */
    public void rechunk(int maxChunkSize) {

    }


    // /////////////////////////////////////////////////////////////////
    //   Methods
    // /////////////////////////////////////////////////////////////////

    /*
     * Return number of chunks.
     */
    public int chunkCount() {
	return this.chunks.size();
    }


    /*
     * Returns the file tied used by the splitter.
     */
    public File getFile() {
	return this.file;
    }


    /*
     * Returns all the file's chunks.
     */
    public ArrayList<byte[]> getChunks() {
	return this.chunks;
    }


    /*
     * Get bytes for a file chunk at index
     */
    public byte[] getChunk(int index) throws IndexOutOfBoundsException{
	return this.chunks.get(index);
    }


}
