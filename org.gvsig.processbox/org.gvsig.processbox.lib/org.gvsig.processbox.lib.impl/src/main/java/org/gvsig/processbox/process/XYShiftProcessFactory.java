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
package org.gvsig.processbox.process;

import org.gvsig.fmap.geom.Geometry;
import org.gvsig.processbox.lib.api.Process;
import org.gvsig.processbox.lib.api.ProcessBoxLocator;
import org.gvsig.processbox.lib.api.ProcessBoxManager;
import org.gvsig.processbox.lib.spi.AbstractProcessFactory;
import org.gvsig.tools.dataTypes.DataTypes;
import org.gvsig.tools.util.ListBuilder;
import org.gvsig.processbox.lib.api.ParametersDefinitionBuilder;

/**
 *
 * @author jjdelcerro
 */
public class XYShiftProcessFactory extends AbstractProcessFactory {

    public static final String ID = "xyshift";
    public static final String NAME = "XYShift";
    public static final String DESCRIPTION = null;

    public static final String GROUP_NAME = "basic_vect_algorithms";

    public static final String RESULT_POL = "RESULT_POL";
    public static final String RESULT_POINT = "RESULT_POINT";
    public static final String RESULT_LINE = "RESULT_LINE";

    public static final String LAYER = "LAYER";
//    public static final String USE_SELECTION = "USE_SELECTION";
    public static final String X = "X";
    public static final String Y = "Y";

    
    public XYShiftProcessFactory() {
        super(ID, NAME, GROUP_NAME, DESCRIPTION);
        this.defineParameters();
    }

    private void defineParameters() {
        ProcessBoxManager manager = ProcessBoxLocator.getProcessBoxManager();
        ParametersDefinitionBuilder builder = manager.createParametersBuilder(ID, 
                ParametersDefinitionBuilder.MODE_INPUT
        );
        builder.addInputVectorChannel(
                LAYER, 
                "Input layer", 
                null, 
                true,
                new ListBuilder<Integer>()
                    .add(Geometry.TYPES.GEOMETRY)
                    .asList()
        );
        builder.addCalculatedValue(
                X, 
                "X shift", 
                null, 
                DataTypes.DOUBLE, 
                0, 
                LAYER
        );
        builder.addCalculatedValue(
                Y, 
                "Y shift", 
                null, 
                DataTypes.DOUBLE, 
                0,
                LAYER
        );
        this.setInputParametersDefinition(builder.asParametersDefinition());

        builder = manager.createParametersBuilder(ID, 
                ParametersDefinitionBuilder.MODE_OUTPUT
        );
        builder.addOutputVectorChannel(
                RESULT_POL, 
                "Output polygons", 
                null, 
                false,
                Geometry.TYPES.MULTIPOLYGON
        );
        builder.addOutputVectorChannel(
                RESULT_LINE, 
                "Output lines", 
                null, 
                false,
                Geometry.TYPES.MULTILINE
        );
        builder.addOutputVectorChannel(
                RESULT_POINT, 
                "Output points", 
                null, 
                false,
                Geometry.TYPES.MULTIPOINT
        );
        this.setOutputParametersDefinition(builder.asParametersDefinition());
    }

    @Override
    public Process createProcess() {
        XYShiftProcess process = new XYShiftProcess(this);
        return process;
    }

}
