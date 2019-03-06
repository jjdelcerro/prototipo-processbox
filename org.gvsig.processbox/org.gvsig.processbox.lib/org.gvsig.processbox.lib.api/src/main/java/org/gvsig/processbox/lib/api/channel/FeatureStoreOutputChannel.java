package org.gvsig.processbox.lib.api.channel;

import org.gvsig.fmap.dal.feature.EditableFeatureType;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.geom.type.GeometryType;

/**
 *
 * @author jjdelcerro
 */
public interface FeatureStoreOutputChannel extends OutputChannel {

    public void setFeatureType(EditableFeatureType newType);

    public GeometryType getGeometryType();
    
    public FeatureStore getStore();

}
