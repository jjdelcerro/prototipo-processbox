package org.gvsig.processbox.lib.impl;

import org.gvsig.tools.dynobject.DynField;
import org.gvsig.tools.dynobject.DynField_v2;

/**
 *
 * @author jjdelcerro
 */
public class BaseComplexParameter implements ComplexParameter {

    private DynField_v2 parameterDefinition;

    public void setParameterDefinition(DynField parameterDefinition) {
        this.parameterDefinition = (DynField_v2) parameterDefinition;
    }

    public DynField_v2 getParameterDefinition() {
        return this.parameterDefinition;
    }

}
