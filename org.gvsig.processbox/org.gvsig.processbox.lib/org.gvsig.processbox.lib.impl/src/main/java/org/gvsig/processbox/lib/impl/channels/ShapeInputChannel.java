package org.gvsig.processbox.lib.impl.channels;

import java.io.File;
import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.processbox.lib.api.channel.InputFileChannel;

/**
 *
 * @author jjdelcerro
 */
public class ShapeInputChannel implements InputFileChannel {

    private File file;

    public ShapeInputChannel(File file) {
        this.file = file;
    }
    
    @Override
    public boolean exists() {
        if( this.file == null ) {
            return false;
        }
        return this.file.exists();
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
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
}
