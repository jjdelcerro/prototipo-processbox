package org.gvsig.processbox.swing.api;

import org.gvsig.tools.dynobject.DynObject;
import org.gvsig.tools.swing.api.Component;

/**
 *
 * @author jjdelcerro
 */
public interface JProcessParameters extends Component {
    
    public void put(DynObject parameters);
    
    public DynObject fetch(DynObject parameters);
}
