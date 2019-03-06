package org.gvsig.processbox.lib.api.channel;

import org.gvsig.tools.util.HasAFile;

/**
 *
 * @author jjdelcerro
 */
public interface FileInputChannel extends HasAFile, InputChannel {

    public boolean exists();
    
}
