package org.gvsig.processbox.lib.api;

import java.util.List;
import org.gvsig.tools.dynobject.DynStruct;

/**
 *
 * @author jjdelcerro
 */
public interface ParametersDefinitionBuilder {

    public static final String MODE_INPUT = "InputParameters";
    public static final String MODE_OUTPUT = "OutputParameters";
    
    public static String GEOMETRY_TYPES = "geometryTypes";
    public static String GEOMETRY_TYPE = "geometryType";

    
    public DynStruct asParametersDefinition();
    
    public void addBoolean(
            String name, 
            String label, 
            String description, 
            boolean defaultValue
    );
    
    public void addNumericalValue(
            String name, 
            String label, 
            String description, 
            int type, 
            Number defaultValue
    );
    
    public void addInputVectorChannel(
            String name, 
            String label, 
            String description, 
            boolean mandatory,
            List<Integer> geometryTypes
    );

    public void addOutputVectorChannel(
            String name, 
            String label, 
            String description, 
            boolean mandatory,
            int geometryType
    );


    public void addCalculatedValue(
            String name, 
            String label, 
            String description, 
            int type, 
            Object defaultValue,
            String parentParameterName
    );
}
