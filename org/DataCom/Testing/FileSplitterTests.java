package org.DataCom.Testing;

import java.io.*;
import java.net.*;
import org.DataCom.Server.*;
import org.DataCom.Client.*;
import java.util.*;

public class FileSplitterTests {
    public static void main(String[] args) {
	try {
	    UFTFileSplitter splitter = new UFTFileSplitter("org/DataCom/Testing/TestData.txt");
	    for (byte[] ba : splitter.getChunks()) {
		for (byte b : ba) {
		    System.out.print(b+ " ");
		}
		System.out.println("---------- end of chunk ------------");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
