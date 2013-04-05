package org.DataCom.Utility;

import java.math.BigInteger;

public enum UFTHeaderType {

    GET, //Request a File
    ACK, //Acknowledge
    ERR, //Error
    DAT, //File Data Packet
    END; //Done sending


    /*
     * Create a header type enum from some bytes
     */
    public static UFTHeaderType fromBytes(byte[] bytes){
	return UFTHeaderType.values()[new BigInteger(bytes).intValue()];
    }
}
