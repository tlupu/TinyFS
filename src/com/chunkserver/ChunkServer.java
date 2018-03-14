package com.chunkserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.interfaces.ChunkServerInterface;

/**
 * implementation of interfaces at the chunkserver side
 * 
 * @author Shahram Ghandeharizadeh
 *
 */

public class ChunkServer implements ChunkServerInterface {
	final static String filePath = "C:\\Users\\teodoralupu\\TinyFS\\TinyFS\\Chunks"; // or C:\\newfile.txt
	public static long counter;

	/**
	 * Initialize the chunk server
	 */
	public ChunkServer() {
		System.out.println(
				"Constructor of ChunkServer is invoked:  Part 1 of TinyFS must implement the body of this method.");
		System.out.println("It does nothing for now.\n");
		
		/* initialize the counter that keeps track of the chunkhandle 
		   counter should start from the number of files that already exist */
		File directory = new File(filePath);
		System.out.println("Initialized file.\n");
		
		if(directory.list() != null && directory.list().length > 0)
		{
			counter = directory.list().length;
		}
		else
		{
			counter = 0;
		}
	    
	    System.out.println("counter: " + counter + "\n");
	}

	/**
	 * Each chunk corresponds to a file. Return the chunk handle of the last chunk
	 * in the file.
	 */
	public String initializeChunk() {
		System.out.println("createChunk invoked:  Part 1 of TinyFS must implement the body of this method.");
		System.out.println("Returns null for now.\n");
//		return null;
		
		// increment the counter
		counter++;
		String counterStr = Long.toString(counter);
		
		// return the counter
		return counterStr;
	}

	/**
	 * Write the byte array to the chunk at the specified offset The byte array size
	 * should be no greater than 4KB
	 */
	public boolean putChunk(String ChunkHandle, byte[] payload, int offset) {
		System.out.println("writeChunk invoked:  Part 1 of TinyFS must implement the body of this method.");
//		System.out.println("Returns false for now.\n");
//		return false;
		
		String counterStr = Long.toString(counter);
		// initialize a binary file with the counter as the filename
		String fPath = filePath + "\\" + counterStr + ".bin";
		File myFile = new File(fPath);
		
		if (payload.length > ChunkServer.ChunkSize) {
			System.out.println("The chunk size is greater than 4KB!");
			return false;
		}
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(myFile);
			/* To write byte array to a file, use 
			 * void write(byte[] bArray) method of Java FileOutputStream class. 
			 * This method writes given byte array to a file. */

			fos.write(payload, offset, payload.length);

			/* Close FileOutputStream using, void close() method of Java FileOutputStream class. */
			fos.close();
			
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;

	}

	/**
	 * read the chunk at the specific offset
	 */
	public byte[] getChunk(String ChunkHandle, int offset, int NumberOfBytes) {
		System.out.println("readChunk invoked:  Part 1 of TinyFS must implement the body of this method.");
		System.out.println("Returns null for now.\n");
		
		// initialize a binary file with the counter as the filename
		String fPath = filePath + "\\" + ChunkHandle + ".bin";
		File myFile = new File(fPath);

		FileInputStream fin = null;
		byte fileContent[] = null;
		try {
			// create FileInputStream object
			fin = new FileInputStream(myFile);

			fileContent = new byte[(int)myFile.length()];
			
			// Reads up to certain bytes of data from this input stream into an array of bytes.
			fin.read(fileContent);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found" + e);
		}
		catch (IOException ioe) {
			System.out.println("Exception while reading file " + ioe);
		}
		
		return fileContent;
	}

}
