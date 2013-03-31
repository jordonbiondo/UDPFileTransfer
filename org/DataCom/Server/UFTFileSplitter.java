package org.DataCom.Server;

import java.util.*;
import java.io.*;
import java.net.*;


public class UFTFileSplitter {

    /*
     * Default size, in bytes, for each chunk
     */
    public static final int DEFAULT_CHUNK_SIZE = 512;


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



    // /////////////////////////////////////////////////////////////////
    //   Constructors
    // /////////////////////////////////////////////////////////////////

    /*
     * Create a new splitter for a given file
     *
     * Main Constructor to be filled in
     */
    public UFTFileSplitter(File file, int maxChunkSize) throws IOException {
	this.chunks = new ArrayList<byte[]>();
	this.file = file;
	this.chunk();
    }


    /*
     * Create a new splitter from a filename
     */
    public UFTFileSplitter(String filename, int maxChunkSize) throws FileNotFoundException,
								     IOException {

	this(new File(filename), maxChunkSize);
    }


    /*
     * Create a new splitter for a given file
     */
    public UFTFileSplitter(File file) throws IOException{
	this(file, DEFAULT_CHUNK_SIZE);
    }


    /*
     * Create a new splitter from a filename
     */
    public UFTFileSplitter(String filename) throws FileNotFoundException,
						   IOException {
	this(new File(filename));
    }


    /*
     * Chunk up dat file. mkay
     */
    private void chunk() throws IOException {
	chunks.clear();
	if(!this.file.canRead()) {
	    throw new IOException("File: "+file.getName()+" cannot be read, ensure permissions");
	}
	try {
	    FileInputStream fileStream = new FileInputStream(file);
	    int chunkIndex = 0;
	    for (int availBytes = fileStream.available(); availBytes > 0;
		 availBytes = fileStream.available()) {
		int bytesToGrab = maxChunkSize < availBytes ? maxChunkSize : availBytes;
		System.out.println("avail: "+availBytes+"\nmax: "+maxChunkSize);
		byte[] currentChunk = new byte[bytesToGrab];
		// read the bytes into the buffer
		if(fileStream.read(currentChunk) != -1) {
		    chunks.add(chunkIndex, currentChunk);
		} else {
		    throw new IOException("bytes stream  for " +
					  file.getName() + " ended unexpectedly");
		}
		chunkIndex++;
	    }

	} catch(FileNotFoundException fnfe) {
	    fnfe.printStackTrace();
	} catch(Exception e) {
	    e.printStackTrace();
	    // do stuff
	}

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
    public byte[] getChunk(int index) {
	return this.chunks.get(index);
    }


}
