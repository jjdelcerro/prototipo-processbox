package org.gvsig.processbox.lib.impl;

import org.gvsig.tools.dynobject.DynField;
import org.gvsig.tools.dynobject.DynField_v2;

/**
 *
 * @author jjdelcerro
 */
public interface ComplexParameter {
    
    public void setParameterDefinition(DynField parameterDefinition);
    
    public DynField_v2 getParameterDefinition();
}
