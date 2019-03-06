package org.gvsig.processbox.lib.impl.coerce;

import java.io.File;
import org.gvsig.processbox.lib.impl.channels.ShapeOutputChannel;
import org.gvsig.tools.dataTypes.CoercionException;
import org.gvsig.tools.dataTypes.DataTypesManager.Coercion;
import org.gvsig.processbox.lib.api.channel.FileOutputChannel;

/**
 *
 * @author jjdelcerro
 */
public class CoerceToFileOutputChannel implements Coercion {

    @Override
    public Object coerce(Object value) throws CoercionException {
        if( value == null ) {
            return null;
        }
        if( value instanceof FileOutputChannel ) {
            return value;
        }
        if( value instanceof File ) {
            // FIXME: habria que mirar las extensiones para ver que creamos
            ShapeOutputChannel channel = new ShapeOutputChannel();
            channel.setFile((File) value);
            return channel;
        }
        throw new CoercionException("Can't coerce '"+value.getClass().getName()+"' to FileOutputChannel.");
    }
    
}
