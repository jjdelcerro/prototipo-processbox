package org.gvsig.processbox.lib.impl.coerce;

import java.io.File;
import java.net.URL;
import org.gvsig.processbox.lib.api.CalculatedValue;
import org.gvsig.tools.dataTypes.CoercionException;
import org.gvsig.tools.dataTypes.DataTypesManager.Coercion;
import org.gvsig.processbox.lib.impl.DefaultCalculatedValue;

/**
 *
 * @author jjdelcerro
 */
public class CoerceToCalculatedValue implements Coercion {

    @Override
    public Object coerce(Object value) throws CoercionException {
        if( value == null ) {
            return null;
        }
        if( value instanceof CalculatedValue ) {
            return value;
        }
        if( value instanceof Number ) {
            CalculatedValue calculated = new DefaultCalculatedValue() ;
            calculated.set(value);
            return calculated;
        }
        if( value instanceof String ) {
            CalculatedValue calculated = new DefaultCalculatedValue() ;
            calculated.set(value);
            return calculated;
        }
        if( value instanceof File ) {
            CalculatedValue calculated = new DefaultCalculatedValue() ;
            calculated.set(value);
            return calculated;
        }
        if( value instanceof URL ) {
            CalculatedValue calculated = new DefaultCalculatedValue() ;
            calculated.set(value);
            return calculated;
        }
        throw new CoercionException("Can't coerce '"+value.getClass().getName()+"' to CalculatedValue.");
    }
    
}
