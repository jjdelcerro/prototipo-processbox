package org.gvsig.processbox.lib.impl;

import org.gvsig.processbox.lib.api.CalculatedValue;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.processbox.lib.api.channel.Channel;
import org.gvsig.processbox.lib.api.ProcessParameters;
import org.gvsig.processbox.lib.api.channel.FeatureStoreInputChannel;
import org.gvsig.processbox.lib.api.channel.FeatureStoreOutputChannel;
import org.gvsig.processbox.lib.impl.channels.DefaultFeatureStoreInputChannel;
import org.gvsig.processbox.lib.impl.channels.ShapeOutputChannel;
import org.gvsig.tools.dynobject.DynField;
import org.gvsig.tools.dynobject.DynObject;
import org.gvsig.tools.dynobject.DynStruct;

/**
 *
 * @author jjdelcerro
 */
public class DefaultProcessParameters implements ProcessParameters {

    private final DynObject params;
    private Feature currentFeature;

    public DefaultProcessParameters(DynObject parameters) {
        this.params = parameters;
        for (DynField field : this.params.getDynClass().getDynFields()) {
            if (this.params.getDynValue(field.getName()) != null) {
                continue;
            }
            if (field.getDataType().getType() == ProcessBoxImplLibrary.typeCalculatedValue) {
                this.params.setDynValue(
                        field.getName(),
                        new DefaultCalculatedValue(field)
                );

            } else if (field.getDataType().getType() == ProcessBoxImplLibrary.typeFeatureStoreInputChannel) {
                this.params.setDynValue(
                        field.getName(),
                        new DefaultFeatureStoreInputChannel(field)
                );

            } else if (field.getDataType().getType() == ProcessBoxImplLibrary.typeShapeOutputChannel) {
                this.params.setDynValue(
                        field.getName(),
                        new ShapeOutputChannel(field)
                );

            }
        }
    }

    public void setCurrentFeature(Feature feature) {
        this.currentFeature = feature;
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
        Object value = this.params.getDynValue(name);
        if( value instanceof ComplexParameter ) {
            ComplexParameter complex = (ComplexParameter) value;
            if( complex.getParameterDefinition()==null ) {
                complex.setParameterDefinition(this.getDefinition().addDynField(name));
            }
        }
        if( value instanceof CalculatedValue ) {
            value = ((CalculatedValue)value).get(this.currentFeature);
        }
        return value;
    }

    @Override
    public void set(String name, Object value) {
        this.params.setDynValue(name, value);
    }

    @Override
    public int getAsInt(String name) {
        return (Integer) this.get(name);
    }

    @Override
    public long getAsLong(String name) {
        return (Long) this.get(name);
    }

    @Override
    public float getAsFloat(String name) {
        return (Float) this.get(name);
    }

    @Override
    public double getAsDouble(String name) {
        return (Double) this.get(name);
    }

    @Override
    public boolean getAsBoolean(String name) {
        return (Boolean) this.get(name);
    }

    @Override
    public String getAsString(String name) {
        return (String) this.get(name);
    }

    @Override
    public DataStore getAsDataStore(String name) {
        return (DataStore) this.get(name);
    }

    @Override
    public FeatureStore getAsFeatureStore(String name) {
        return (FeatureStore) this.get(name);
    }

    @Override
    public FeatureStoreOutputChannel getAsFeatureStoreOutputChannel(String name) {
        return (FeatureStoreOutputChannel) this.get(name);
    }

    @Override
    public Channel getAsChannel(String name) {
        return (Channel) this.get(name);
    }

    @Override
    public FeatureStoreInputChannel getAsFeatureStoreInputChannel(String name) {
        return (FeatureStoreInputChannel) this.get(name);
    }

}
