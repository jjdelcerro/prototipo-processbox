package org.gvsig.processbox.lib.api;

/**
 *
 * @author jjdelcerro
 */
public class ProcessBoxUtils {

    public static ProcessBuilder processBuilder(String processID) {
        return ProcessBoxLocator.getProcessBoxManager().createProcessBuilder(processID);
    }
    
    
}
