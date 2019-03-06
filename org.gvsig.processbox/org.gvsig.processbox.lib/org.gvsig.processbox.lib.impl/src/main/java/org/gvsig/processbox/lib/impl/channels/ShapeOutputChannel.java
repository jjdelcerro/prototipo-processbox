package org.gvsig.processbox.lib.impl.channels;

import java.io.File;
import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.EditableFeatureType;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.GeometryLocator;
import org.gvsig.fmap.geom.type.GeometryType;
import static org.gvsig.processbox.lib.api.ProcessParameters.TAG_OUTPUTVECTORCHANNEL_GEOMETRY_TYPE;
import org.gvsig.processbox.lib.api.channel.FeatureStoreOutputChannel;
import org.gvsig.processbox.lib.api.channel.FileOutputChannel;
import org.gvsig.processbox.lib.impl.BaseComplexParameter;
import org.gvsig.tools.dynobject.DynField;
import org.gvsig.tools.dynobject.DynField_v2;
import org.gvsig.tools.dynobject.Tags;

/**
 *
 * @author jjdelcerro
 */
@SuppressWarnings("UseSpecificCatch")
public class ShapeOutputChannel 
        extends BaseComplexParameter
        implements FeatureStoreOutputChannel,  FileOutputChannel 
    {

    private EditableFeatureType featureType;
    private File file;
    private FeatureStore store;

    public ShapeOutputChannel() {
        this.file = null;
        this.store = null;
        this.featureType = null;
    }
    
    public ShapeOutputChannel(DynField field) {
        this();
        this.setParameterDefinition(field);
    }
    
    @Override
    public GeometryType getGeometryType() {
        try {
            Tags tags = this.getParameterDefinition().getTags();
            Integer type = (Integer) tags.get(TAG_OUTPUTVECTORCHANNEL_GEOMETRY_TYPE);
            if( type==null ) {
                return null;
            }
            GeometryType geomtype = GeometryLocator.getGeometryManager().getGeometryType(
                    type, Geometry.SUBTYPES.UNKNOWN
            );
            return geomtype;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean exists() {
        File f = this.getFile();
        if( f == null ) {
            return false;
        }
        return f.exists();
    }

    @Override
    public void setFeatureType(EditableFeatureType featureType) {
        this.featureType = featureType;
    }

    @Override
    public void create() {
//        DataManager dataManager = DALLocator.getDataManager();
//        File folder = this.getFile().getParentFile();
//        DataServerExplorer explorer = dataManager.openServerExplorer("?????", "file", folder);
//        NewFeatureStoreParameters addparams = (NewFeatureStoreParameters) explorer.getAddParameters("shape");
//        addparams.setDefaultFeatureType(this.featureType);
//        explorer.add("shape", addparams, true);
    }

    @Override
    public FeatureStore getStore() {
        if( this.store == null ) {
            try {
                DataManager dataManager = DALLocator.getDataManager();
                this.store = (FeatureStore) dataManager.openStore(
                        "Shape",
                        "file", this.getFile()
                );
            } catch (Exception ex) { // TODO: falta por gestionar los errores
                return null;
            }
        }
        return this.store;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }
    
}
