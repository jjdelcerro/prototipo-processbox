package org.gvsig.processbox.lib.impl.channels;

import java.util.List;
import org.gvsig.expressionevaluator.Expression;
import org.gvsig.expressionevaluator.ExpressionUtils;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.processbox.lib.api.ProcessParameters;
import org.gvsig.processbox.lib.api.channel.FeatureStoreInputChannel;
import org.gvsig.processbox.lib.impl.BaseComplexParameter;
import org.gvsig.tools.dynobject.DynField;
import org.gvsig.tools.dynobject.Tags;

/**
 *
 * @author jjdelcerro
 */
public class DefaultFeatureStoreInputChannel 
        extends BaseComplexParameter
        implements FeatureStoreInputChannel {

    private FeatureStore store;
    private boolean useSelection;
    private Expression filter;

    public DefaultFeatureStoreInputChannel() {
        this.store = null;
        this.useSelection = false;
        this.filter = null;
    }

    public DefaultFeatureStoreInputChannel(DynField field) {
        this.setParameterDefinition(field);
    }
    
    public List<Integer> getValidGeometryTypes() {
        Tags tags = this.getParameterDefinition().getTags();
        List<Integer> validGeometryTypes = (List<Integer>) tags.get(
                ProcessParameters.TAG_INPUTVECTORCHANNEL_GEOMETRY_TYPES
        );
        return validGeometryTypes;
    }
    
    @Override
    public FeatureStore getStore() {
        return this.store;
    }
    
    public void setStore(FeatureStore store) {
        this.store = store;
    }

    public Expression getFilter() {
        return this.filter;
    }

    @Override
    public boolean getUseSelection() {
        return this.useSelection;
    }

    @Override
    public FeatureStoreInputChannel setFilter(Expression filter) {
        this.filter = filter;
        return null;
    }

    @Override
    public FeatureStoreInputChannel setFilter(String filter) {
        this.filter = ExpressionUtils.createExpression(filter);
        return this;
    }

    @Override
    public FeatureStoreInputChannel setUseSelection(boolean useSelection) {
        this.useSelection = useSelection;
        return this;
    }
    
}
