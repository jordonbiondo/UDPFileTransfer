package org.DataCom.Utility;

/**
 * Debug Tools
 */
public class Debug {

    /**
     * Debug mode
     */
    public static boolean ON = true;


    /**
     * hackishly print the callers code place
     */
    private static void put(String prefix, Object o) {
	if (Debug.ON)
	    System.out.println(prefix+": "+Thread.currentThread().getStackTrace()[3] +":\n\t"+o.toString());
    }

    /**
     * If debug, print callers class name followed by the message in O
     */
    public static void pln(Object o) { put("debug", o); }


    /**
     * Debug error message
     */
    public static void err(Object o) { put("debug ERROR", o); }


}
