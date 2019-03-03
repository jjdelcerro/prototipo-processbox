package org.gvsig.processbox.lib.api;

/**
 *
 * @author jjdelcerro
 */
public interface Process {

    public String getID();
    
    public ProcessFactory getFactory();
    
    public void execute(ProcessParameters input, ProcessParameters output);
    
}
