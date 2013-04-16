package org.DataCom.Utility;

import java.math.BigInteger;

/**
 * UFT Header type
 */
public enum UFTHeaderType {

    // /////////////////////////////////////////////////////////////////
    //  Enumerated Values
    // /////////////////////////////////////////////////////////////////
    GET, //Request a File

    ACK, //Acknowledge

    ERR, //Error

    DAT, //File Data Packet

    END; //Done sending



    // /////////////////////////////////////////////////////////////////
    //  Methods
    // /////////////////////////////////////////////////////////////////
    /**
     * Create a header type enum from some bytes
     */
    public static UFTHeaderType fromBytes(byte[] bytes){
	return UFTHeaderType.values()[new BigInteger(bytes).intValue()];
    }
}
