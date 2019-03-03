
package org.gvsig.processbox.swing.impl;

import org.gvsig.processbox.swing.api.JProcessBox;
import org.gvsig.processbox.swing.api.JProcessParameters;
import org.gvsig.processbox.swing.api.ProcessBoxSwingManager;
import org.gvsig.processbox.swing.api.ProcessBoxSwingServices;

/**
 *
 * @author jjdelcerro
 */
public class DefaultProcessBoxSwingManager implements ProcessBoxSwingManager {

    private ProcessBoxSwingServices services;

    @Override
    public void setDefaultServices(ProcessBoxSwingServices services) {
        this.services = services;
    }

    @Override
    public ProcessBoxSwingServices getDefaultServices() {
        return this.services;
    }

    @Override
    public JProcessBox createJProcessBox() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public JProcessParameters createJProcessParameters(String processID) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
