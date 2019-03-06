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
package org.gvsig.processbox.lib.impl;

import org.gvsig.fmap.dal.DALLibrary;
import org.gvsig.fmap.geom.GeometryLibrary;
import org.gvsig.processbox.lib.api.ProcessBoxLibrary;
import org.gvsig.processbox.lib.api.ProcessBoxLocator;
import org.gvsig.processbox.lib.api.ProcessBoxManager;
import org.gvsig.processbox.lib.impl.channels.DefaultFeatureStoreInputChannel;
import org.gvsig.processbox.lib.impl.channels.ShapeOutputChannel;
import org.gvsig.processbox.lib.impl.coerce.CoerceToCalculatedValue;
import org.gvsig.processbox.lib.impl.coerce.CoerceToFeatureStoreInputChannel;
import org.gvsig.processbox.lib.impl.coerce.CoerceToFileOutputChannel;
import org.gvsig.tools.ToolsLibrary;
import org.gvsig.tools.library.AbstractLibrary;
import org.gvsig.tools.library.LibraryException;
import org.gvsig.processbox.process.XYShiftProcessFactory;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.dataTypes.DataTypesManager;

/**
 *
 * @author jjdelcerro
 */
public class ProcessBoxImplLibrary extends AbstractLibrary {

    static int typeShapeOutputChannel = -1;
    static int typeCalculatedValue = -1;
    static int typeFeatureStoreInputChannel = -1;
    
    @Override
    public void doRegistration() {
        super.doRegistration();
        registerAsImplementationOf(ProcessBoxLibrary.class);
        this.require(ToolsLibrary.class);
        this.require(DALLibrary.class);
        this.require(GeometryLibrary.class);
    }

    @Override
    protected void doInitialize() throws LibraryException {
        ProcessBoxLocator.registerDefaultProcessBoxManager(DefaultProcessBoxManager.class);
        
        DataTypesManager dataTypesManager = ToolsLocator.getDataTypesManager();
        this.typeFeatureStoreInputChannel = dataTypesManager.addtype(
                0, 
                null, 
                "ProcessBoxFeatureStoreInputChannel", 
                DefaultFeatureStoreInputChannel.class, 
                new CoerceToFeatureStoreInputChannel()
        );
        this.typeShapeOutputChannel = dataTypesManager.addtype(
                0, 
                null, 
                "ProcessBoxShapeOutputChannel", 
                ShapeOutputChannel.class, 
                new CoerceToFileOutputChannel()
        );
        this.typeCalculatedValue = dataTypesManager.addtype(
                0, 
                null, 
                "ProcessBoxCalculatedValue", 
                DefaultCalculatedValue.class, 
                new CoerceToCalculatedValue()
        );

    }

    @Override
    protected void doPostInitialize() throws LibraryException {
        ProcessBoxManager manager = ProcessBoxLocator.getProcessBoxManager();
        manager.addProcessFactories(new XYShiftProcessFactory());
    }

}
