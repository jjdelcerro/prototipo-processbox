package org.gvsig.processbox.lib.impl.coerce;

import java.io.File;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.processbox.lib.api.channel.FeatureStoreInputChannel;
import org.gvsig.processbox.lib.impl.channels.DefaultFeatureStoreInputChannel;
import org.gvsig.processbox.lib.impl.channels.ShapeInputChannel;
import org.gvsig.processbox.lib.impl.channels.ShapeOutputChannel;
import org.gvsig.tools.dataTypes.CoercionException;
import org.gvsig.tools.dataTypes.DataTypesManager.Coercion;

/**
 *
 * @author jjdelcerro
 */
public class CoerceToFeatureStoreInputChannel implements Coercion {

    @Override
    public Object coerce(Object value) throws CoercionException {
        if( value == null ) {
            return null;
        }
        if( value instanceof FeatureStoreInputChannel ) {
            return value;
        }
        if( value instanceof FeatureStore ) {
            DefaultFeatureStoreInputChannel channel = new DefaultFeatureStoreInputChannel();
            channel.setStore((FeatureStore) value);
            return channel ;
        }
        if( value instanceof File ) {
            // FIXME: habria que mirar las extensiones para ver que creamos
            ShapeInputChannel channel = new ShapeInputChannel();
            channel.setFile((File) value);
            return channel ;
        }
        throw new CoercionException("Can't coerce '"+value.getClass().getName()+"' to FeatureStoreInputChannel.");
    }
    
}
