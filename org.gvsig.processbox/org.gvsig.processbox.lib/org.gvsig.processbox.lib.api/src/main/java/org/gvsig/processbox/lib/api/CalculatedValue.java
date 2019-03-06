package org.gvsig.processbox.lib.api;

import org.gvsig.fmap.dal.feature.Feature;

/**
 *
 * @author jjdelcerro
 */
public interface CalculatedValue {
    
    public Object get();

    public Object get(Feature feature);
    
    public void set(Object value);
    
    public boolean isConstant();
    
    public void setConstant(boolean constant);
    
}
