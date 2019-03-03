package org.gvsig.processbox.lib.impl.coerce;

import java.io.File;
import org.gvsig.processbox.lib.api.channel.OutputFileChannel;
import org.gvsig.processbox.lib.impl.channels.ShapeOutputChannel;
import org.gvsig.tools.dataTypes.CoercionException;
import org.gvsig.tools.dataTypes.DataTypesManager.Coercion;

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
        if( value instanceof OutputFileChannel ) {
            return value;
        }
        if( value instanceof File ) {
            // FIXME: habria que mirar las extensiones para ver que creamos
            return new ShapeOutputChannel((File) value);
        }
        throw new CoercionException("Can't coerce '"+value.getClass().getName()+"' to ShapeOutputChannel.");
    }
    
}
