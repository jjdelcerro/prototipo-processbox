package org.gvsig.processbox.lib.api;

import org.gvsig.tools.task.MonitorableTask;

/**
 *
 * @author jjdelcerro
 */
public interface Process extends MonitorableTask {

    public String getID();
    
    public ProcessFactory getFactory();
    
    public void execute(ProcessParameters input, ProcessParameters output);
    
}
