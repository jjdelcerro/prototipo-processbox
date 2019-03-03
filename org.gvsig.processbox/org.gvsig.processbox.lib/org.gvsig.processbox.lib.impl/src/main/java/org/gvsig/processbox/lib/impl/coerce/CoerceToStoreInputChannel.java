package org.gvsig.processbox.lib.impl.coerce;

import java.io.File;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.processbox.lib.impl.channels.ShapeInputChannel;
import org.gvsig.processbox.lib.impl.channels.ShapeOutputChannel;
import org.gvsig.processbox.lib.impl.channels.StoreInputChannel;
import org.gvsig.tools.dataTypes.CoercionException;
import org.gvsig.tools.dataTypes.DataTypesManager.Coercion;

/**
 *
 * @author jjdelcerro
 */
public class CoerceToStoreInputChannel implements Coercion {

    @Override
    public Object coerce(Object value) throws CoercionException {
        if( value == null ) {
            return null;
        }
        if( value instanceof StoreInputChannel ) {
            return value;
        }
        if( value instanceof DataStore ) {
            return new StoreInputChannel((DataStore) value);
        }
        if( value instanceof File ) {
            // FIXME: habria que mirar las extensiones para ver que creamos
            return new ShapeInputChannel((File) value);
        }
        throw new CoercionException("Can't coerce '"+value.getClass().getName()+"' to CoerceToStoreInputChannel.");
    }
    
}
