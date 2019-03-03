package org.gvsig.processbox.lib.api.channel;

import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.FeatureStore;

/**
 *
 * @author jjdelcerro
 */
public interface Channel {

    public DataStore getStore();
    
    public FeatureStore getFeatureStore();
    
}
