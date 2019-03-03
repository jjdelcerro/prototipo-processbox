/**
 * gvSIG. Desktop Geographic Information System.
 *
 * Copyright (C) 2007-2013 gvSIG Association.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * For any additional information, do not hesitate to contact us
 * at info AT gvsig.com, or visit our website www.gvsig.com.
 */
package org.gvsig.processbox.app.mainplugin;

import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.gvsig.processbox.lib.api.ProcessBoxServices;
import org.gvsig.processbox.swing.api.ProcessBoxSwingServices;
/**
 *
 * @author jjdelcerro
 */
@SuppressWarnings("UseSpecificCatch")
public class ApplicationProcessBoxServices implements ProcessBoxServices, ProcessBoxSwingServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProcessBoxServices.class);

    public ApplicationProcessBoxServices() {
        
    }
    
    @Override
    public ProcessBoxDocument getCurrentDocument() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public ProcessBoxDocument createTableDocument(String name, FeatureStore store) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public ProcessBoxDocument createMapDocument(String name) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void addToMapDocument(String name, DataStore store) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public DataStoreTreeModel createDataStoreTreeModel() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }


}
