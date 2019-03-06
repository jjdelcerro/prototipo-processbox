package org.gvsig.processbox.lib.impl;

import java.util.ArrayList;
import java.util.List;
import org.gvsig.fmap.geom.Geometry;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.dynobject.DynClass;
import org.gvsig.tools.dynobject.DynField_v2;
import org.gvsig.tools.dynobject.DynObjectManager;
import org.gvsig.tools.dynobject.DynStruct;
import org.gvsig.processbox.lib.api.ParametersDefinitionBuilder;
import org.gvsig.processbox.lib.api.ProcessParameters;
import org.gvsig.tools.dataTypes.DataType;
import org.gvsig.tools.dataTypes.DataTypesManager;
import org.gvsig.tools.dynobject.Tags;

/**
 *
 * @author jjdelcerro
 */
public class DefaultParametersDefinitionBuilder implements ParametersDefinitionBuilder {

    public static final String MODE_INPUT = "InputParameters";
    public static final String MODE_OUTPUT = "OutputParameters";
    
    private final DynClass definition;

    public DefaultParametersDefinitionBuilder(String processID, String mode ) {
        DynObjectManager manager = ToolsLocator.getDynObjectManager();
        this.definition = manager.get(processID+mode, "ProcessBox");
    }
    
    @Override
    public DynStruct asParametersDefinition() {
        return this.definition;
    }
    
    public void addBoolean(
            String name, 
            String label, 
            String description, 
            boolean defaultValue
        ) {
        DynField_v2 field = (DynField_v2) this.definition.addDynFieldBoolean(name);
        field.setLabel(label);
        field.setDescription(description);
        field.setDefaultFieldValue(defaultValue);
        field.setMandatory(false);
    }
    
    public void addNumericalValue(
            String name, 
            String label, 
            String description, 
            int type, 
            Number defaultValue
        ) {
        DynField_v2 field = (DynField_v2) this.definition.addDynField(name);
        field.setType(type);
        field.setLabel(label);
        field.setDescription(description);
        field.setDefaultFieldValue(defaultValue);
        field.setMandatory(false);
    }
    
   public void addInputVectorChannel(
            String name, 
            String label, 
            String description, 
            boolean mandatory,
            List<Integer> geometryTypes
    ) {
        DataTypesManager dataTypesManager = ToolsLocator.getDataTypesManager();
        DataType type = dataTypesManager.get(ProcessBoxImplLibrary.typeFeatureStoreInputChannel);
        
        DynField_v2 field = (DynField_v2) this.definition.addDynField(name);
        field.setType(type);
        field.setLabel(label);
        field.setDescription(description);
        field.setMandatory(true);
        if( geometryTypes==null ) {
            geometryTypes = new ArrayList<>();
        }
        if( geometryTypes.isEmpty() ) {
            geometryTypes.add(Geometry.TYPES.GEOMETRY);
        }
        field.getTags().set(ProcessParameters.TAG_INPUTVECTORCHANNEL_GEOMETRY_TYPES, geometryTypes);
   }

    @Override
    public void addOutputVectorChannel(
            String name, 
            String label, 
            String description, 
            boolean mandatory, 
            int geometryType
        ) {
        DataTypesManager dataTypesManager = ToolsLocator.getDataTypesManager();
        DataType type = dataTypesManager.get(ProcessBoxImplLibrary.typeShapeOutputChannel);
        
        DynField_v2 field = (DynField_v2) this.definition.addDynFieldObject(name);
        field.setType(type);
        field.setLabel(label);
        field.setDescription(description);
        field.setMandatory(true);
        field.getTags().set(ProcessParameters.TAG_OUTPUTVECTORCHANNEL_GEOMETRY_TYPE, geometryType);
    }

    @Override
    public void addCalculatedValue(
            String name, 
            String label, 
            String description, 
            int type, 
            Object defaultValue, 
            String parentParameterName
        ) {
        DataTypesManager dataTypesManager = ToolsLocator.getDataTypesManager();
        DataType dataType = dataTypesManager.get(ProcessBoxImplLibrary.typeCalculatedValue);
        
        DynField_v2 field = (DynField_v2) this.definition.addDynFieldObject(name);
        field.setType(dataType);
        field.setLabel(label);
        field.setDescription(description);
        field.setMandatory(true);
        field.setDefaultFieldValue(null);

        Tags tags = field.getTags();
        tags.set(ProcessParameters.TAG_CALCULATED_DEFAULT, defaultValue);
        tags.set(ProcessParameters.TAG_CALCULATED_TYPE, type);
    }
    
    
    
}
