package org.gvsig.processbox.lib.impl;

import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.processbox.lib.api.channel.Channel;
import org.gvsig.processbox.lib.api.channel.InputChannel;
import org.gvsig.processbox.lib.api.channel.OutputChannel;
import org.gvsig.processbox.lib.api.ProcessParameters;
import org.gvsig.tools.dynobject.DynObject;
import org.gvsig.tools.dynobject.DynStruct;

/**
 *
 * @author jjdelcerro
 */
public class DefaultProcessParameters implements ProcessParameters {

    private final DynObject params; 
    
    public DefaultProcessParameters(DynObject parameters) {
        this.params = parameters;
    }

    @Override
    public DynObject get() {
        return this.params;
    }

    @Override
    public DynStruct getDefinition() {
        return this.params.getDynClass();
    }

    @Override
    public Object get(String name) {
        return this.params.getDynValue(name);
    }

    @Override
    public void set(String name, Object value) {
        this.params.setDynValue(name, value);
    }
    
    

    @Override
    public int getAsInt(String name) {
        return (Integer) this.params.getDynValue(name);
    }

    @Override
    public long getAsLong(String name) {
        return (Long) this.params.getDynValue(name);
    }

    @Override
    public float getAsFloat(String name) {
        return (Float) this.params.getDynValue(name);
    }

    @Override
    public double getAsDouble(String name) {
        return (Double) this.params.getDynValue(name);
    }

    @Override
    public boolean getAsBoolean(String name) {
        return (Boolean) this.params.getDynValue(name);
    }

    @Override
    public String getAsString(String name) {
        return (String) this.params.getDynValue(name);
    }

    @Override
    public DataStore getAsDataStore(String name) {
        return (DataStore) this.params.getDynValue(name);
    }

    @Override
    public FeatureStore getAsFeatureStore(String name) {
        return (FeatureStore) this.params.getDynValue(name);
    }

    @Override
    public OutputChannel getAsOutputChannel(String name) {
        return (OutputChannel) this.params.getDynValue(name);
    }

    @Override
    public Channel getAsChannel(String name) {
        return (Channel) this.params.getDynValue(name);
    }

    @Override
    public InputChannel getAsInputChannel(String name) {
        return (InputChannel) this.params.getDynValue(name);
    }
    
}
