package org.DataCom.Testing;

import org.DataCom.Utility.*;
import java.nio.ByteBuffer;

public class TestByteUtils {



    public static void main(String[] args) {
	ByteBuffer buffer = ByteBuffer.allocate(8);
	long x = 12345678;
	buffer.putLong(x);
	byte[] bytes = buffer.array();
	for(byte b : bytes) {
	    System.out.print(b+" ");
	}
	System.out.print("\b\n");
	try {
	    System.out.println(ByteUtils.longVal(bytes));
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }
}
