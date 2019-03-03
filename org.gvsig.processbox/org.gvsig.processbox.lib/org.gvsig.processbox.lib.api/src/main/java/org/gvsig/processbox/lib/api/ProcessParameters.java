package org.gvsig.processbox.lib.api;

import org.gvsig.processbox.lib.api.channel.Channel;
import org.gvsig.processbox.lib.api.channel.OutputChannel;
import org.gvsig.processbox.lib.api.channel.InputChannel;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.tools.dynobject.DynObject;
import org.gvsig.tools.dynobject.DynStruct;

/**
 *
 * @author jjdelcerro
 */
public interface ProcessParameters {

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

    public InputChannel getAsInputChannel(String name);

    public OutputChannel getAsOutputChannel(String name);

    
}
