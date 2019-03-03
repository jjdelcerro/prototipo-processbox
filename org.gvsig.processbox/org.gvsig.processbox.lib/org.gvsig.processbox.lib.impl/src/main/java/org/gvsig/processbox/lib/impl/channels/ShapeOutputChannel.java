package org.gvsig.processbox.lib.impl.channels;

import java.io.File;
import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.DataServerExplorer;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.EditableFeatureType;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.dal.feature.NewFeatureStoreParameters;
import org.gvsig.processbox.lib.api.channel.OutputFileChannel;

/**
 *
 * @author jjdelcerro
 */
@SuppressWarnings("UseSpecificCatch")
public class ShapeOutputChannel implements OutputFileChannel {

    private EditableFeatureType featureType;
    private File file;

    public ShapeOutputChannel(File file) {
        this.file = file;
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
    public DataStore getStore() {
        try {
            DataManager dataManager = DALLocator.getDataManager();
            FeatureStore store = (FeatureStore) dataManager.openStore(
                    "Shape",
                    "file", this.getFile()
            );
            return store;
        } catch (Exception ex) { // TODO: falta por gestionar los errores
            return null;
        }
    }


    @Override
    public FeatureStore getFeatureStore() {
        return (FeatureStore) this.getStore();
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
