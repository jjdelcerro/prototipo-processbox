package org.gvsig.processbox.lib.api;

/**
 *
 * @author jjdelcerro
 */
public interface ProcessBuilder {

    public interface ParamatersProcessBuilder {

        public ParamatersProcessBuilder input(Object... values);

        public ParamatersProcessBuilder output(Object... values);
        
        public ParamatersProcessBuilder set(String name, Object value);
        
        public Process process();
        
        public ProcessParameters params();
        
        public ProcessBuilder execute();
    }
    
    
    public ParamatersProcessBuilder input(Object... values);
    
    public ParamatersProcessBuilder output(Object... values);
    
    public Process process();

    public ProcessBuilder execute();
}
