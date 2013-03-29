package org.DataCom.Utility;

public enum UFTHeaderType {

    GET, //Request a File
    ACK, //Acknowledge
    DAT, //File Data Packet
    END; //Done sending
}
