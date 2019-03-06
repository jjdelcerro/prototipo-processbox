package org.gvsig.processbox.lib.api.channel;

import java.util.List;
import org.gvsig.expressionevaluator.Expression;
import org.gvsig.fmap.dal.feature.FeatureStore;

/**
 *
 * @author jjdelcerro
 */
public interface FeatureStoreInputChannel extends InputChannel {

    public FeatureStore getStore();
    
    public boolean getUseSelection();
    
    public Expression getFilter();
    
    public List<Integer> getValidGeometryTypes();
    
    public FeatureStoreInputChannel setFilter(Expression filter);
    
    public FeatureStoreInputChannel setFilter(String filter);
    
    public FeatureStoreInputChannel setUseSelection(boolean useSelection);
    
    
}
