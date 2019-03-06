package org.gvsig.processbox.lib.api;

import org.gvsig.processbox.lib.api.channel.Channel;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.processbox.lib.api.channel.FeatureStoreInputChannel;
import org.gvsig.processbox.lib.api.channel.FeatureStoreOutputChannel;
import org.gvsig.tools.dynobject.DynObject;
import org.gvsig.tools.dynobject.DynStruct;

/**
 *
 * @author jjdelcerro
 */
public interface ProcessParameters {

    public static final String TAG_CALCULATED_DEFAULT = "TAG_CALCULATED_DEFAULT";
    public static final String TAG_CALCULATED_TYPE = "TAG_CALCULATED_TYPE";
    public static final String TAG_OUTPUTVECTORCHANNEL_GEOMETRY_TYPE = "TAG_OUTPUTVECTORCHANNEL_GEOMETRY_TYPE";
    public static final String TAG_INPUTVECTORCHANNEL_GEOMETRY_TYPES = "TAG_INPUTVECTORCHANNEL_GEOMETRY_TYPES";
    
    public void setCurrentFeature(Feature feature);

    public DynObject get();
    
    public DynStruct getDefinition();
    
    public Object get(String name);
    
    public void set(String name, Object value);
    
    public int getAsInt(String name);
    
    public long getAsLong(String name);
    
    public float getAsFloat(String name);
    
    public double getAsDouble(String name);
    
    public boolean getAsBoolean(String name);
    
    public String getAsString(String name);
    
    public DataStore getAsDataStore(String name);
    
    public FeatureStore getAsFeatureStore(String name);
    
    public Channel getAsChannel(String name);

    public FeatureStoreInputChannel getAsFeatureStoreInputChannel(String name);

    public FeatureStoreOutputChannel getAsFeatureStoreOutputChannel(String name);

    
}
