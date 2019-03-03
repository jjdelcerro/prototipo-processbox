package org.gvsig.processbox.lib.impl.channels;

import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.processbox.lib.api.channel.InputChannel;

/**
 *
 * @author jjdelcerro
 */
public class StoreInputChannel implements InputChannel {

    private final DataStore store;

    public StoreInputChannel(DataStore store) {
        this.store = store;
    }
    
    @Override
    public DataStore getStore() {
        return this.store;
    }

    @Override
    public FeatureStore getFeatureStore() {
        return (FeatureStore) this.store;
    }
    
    
}
