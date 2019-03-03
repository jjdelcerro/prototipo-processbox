package org.gvsig.processbox.lib.api.channel;

import org.gvsig.fmap.dal.feature.EditableFeatureType;

/**
 *
 * @author jjdelcerro
 */
public interface OutputChannel extends Channel {
    public boolean exists();

    public void setFeatureType(EditableFeatureType newType);

    public void create();

}
