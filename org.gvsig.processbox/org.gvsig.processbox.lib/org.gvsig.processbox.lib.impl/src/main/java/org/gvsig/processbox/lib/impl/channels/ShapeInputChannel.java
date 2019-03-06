package org.gvsig.processbox.lib.impl.channels;

import java.io.File;
import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.processbox.lib.api.channel.FeatureStoreInputChannel;
import org.gvsig.processbox.lib.api.channel.FileInputChannel;
import org.gvsig.tools.dynobject.DynField;

/**
 *
 * @author jjdelcerro
 */
@SuppressWarnings("UseSpecificCatch")
public class ShapeInputChannel 
        extends DefaultFeatureStoreInputChannel
        implements FeatureStoreInputChannel, FileInputChannel 
    {

    private File file;

    public ShapeInputChannel() {
        super();
        this.file = null;
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
    public FeatureStore getStore() {
        if( super.getStore() == null ) {
            try {
                DataManager dataManager = DALLocator.getDataManager();
                FeatureStore theStore = (FeatureStore) dataManager.openStore(
                        "Shape",
                        "file", this.getFile()
                );
                super.setStore(theStore);
            } catch (Exception ex) { // TODO: falta por gestionar los errores
                return null;
            }
        }
        return super.getStore();
    }
    
}
