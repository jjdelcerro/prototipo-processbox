package org.gvsig.processbox.lib.impl;

import org.gvsig.expressionevaluator.Expression;
import org.gvsig.expressionevaluator.ExpressionUtils;
import org.gvsig.expressionevaluator.MutableSymbolTable;
import org.gvsig.expressionevaluator.SymbolTable;
import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.expressionevaluator.FeatureSymbolTable;
import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.processbox.lib.api.CalculatedValue;
import org.gvsig.processbox.lib.api.ProcessParameters;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.dataTypes.CoercionException;
import org.gvsig.tools.dataTypes.DataType;
import org.gvsig.tools.dataTypes.DataTypes;
import org.gvsig.tools.dataTypes.DataTypesManager;
import org.gvsig.tools.dynobject.DynField;
import org.gvsig.tools.dynobject.Tags;

/**
 *
 * @author jjdelcerro
 */
public class DefaultCalculatedValue 
        extends BaseComplexParameter
        implements CalculatedValue 
    {

    private DataType dataType;
    private Object value;
    private boolean constant;
    private Expression expression;
    private FeatureSymbolTable featureSymbolTable;
    private MutableSymbolTable symbolTable;

    public DefaultCalculatedValue() {
        this.dataType = null;
        this.value = null;
        this.constant = true;
    }

    public DefaultCalculatedValue(DynField parameterDefinition) {
        this();
        this.setParameterDefinition(parameterDefinition);
    }
    
    public boolean isConstant() {
        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    private Object getDefaultValue() {
        Tags tags = this.getParameterDefinition().getTags();
        Object defaultvalue = tags.get(ProcessParameters.TAG_CALCULATED_DEFAULT);
        return defaultvalue;
    }

    @Override
    public void setParameterDefinition(DynField parameterDefinition) {
        if( super.getParameterDefinition()!=parameterDefinition ) {
            this.dataType = null;
        }
        super.setParameterDefinition(parameterDefinition); 
    }

    private DataType getDataType() {
        if( this.dataType == null ) {
            int type = DataTypes.OBJECT;
            Tags tags = this.getParameterDefinition().getTags();
            try {
                type = tags.getInt(ProcessParameters.TAG_CALCULATED_TYPE);
            } catch (CoercionException ex) {

            }
            DataTypesManager dataTypesManager = ToolsLocator.getDataTypesManager();
            this.dataType = dataTypesManager.get(type);
        }
        return this.dataType;
    }
    
    public void set(Object value) {
        this.value = value;
    }
    
    @Override
    public Object get(Feature feature) {
        Object theValue;
        theValue = this.value;
        if( theValue!=null && !this.constant ) {
            Expression exp = this.getExpression();
            exp.setPhrase(theValue.toString());
            theValue = exp.execute(this.getSymbolTable(feature));
        }
        if( theValue == null ) {
            theValue = this.getDefaultValue();
        }
        try {
            theValue = this.getDataType().coerce(theValue);
        } catch (CoercionException ex) {
            throw new RuntimeException(ex);
        }
        return theValue;
    }

    @Override
    public Object get() {
        return this.get(null);
    }

    private Expression getExpression() {
        if( this.expression == null ) {
            this.expression = ExpressionUtils.createExpression();
        }
        return this.expression;
    }
    
    private FeatureSymbolTable getFeatureSymbolTable() {
        if( this.featureSymbolTable == null ) {
            this.featureSymbolTable = DALLocator.getDataManager().createFeatureSymbolTable();
            this.symbolTable=this.featureSymbolTable.createParent();
        }
        return this.featureSymbolTable;
    }
    
    private SymbolTable getSymbolTable(Feature feature) {
        FeatureSymbolTable fst = this.getFeatureSymbolTable();
        fst.setFeature(feature);
        return this.symbolTable;
    }
}
