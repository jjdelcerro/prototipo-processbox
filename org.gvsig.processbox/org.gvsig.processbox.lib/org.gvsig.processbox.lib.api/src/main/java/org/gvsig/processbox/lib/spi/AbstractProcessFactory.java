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
package org.gvsig.processbox.lib.spi;

import java.util.List;
import org.gvsig.fmap.dal.DataStore;
import org.gvsig.fmap.dal.DataTypes;
import org.gvsig.fmap.dal.feature.FeatureAttributeDescriptor;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.dal.feature.FeatureType;
import org.gvsig.fmap.geom.type.GeometryType;
import org.gvsig.processbox.lib.api.ProcessFactory;
import org.gvsig.tools.dynobject.DynField;
import org.gvsig.tools.dynobject.DynField_v2;
import org.gvsig.tools.dynobject.DynStruct;
import org.gvsig.processbox.lib.api.ParametersDefinitionBuilder;

/**
 *
 * @author jjdelcerro
 */
@SuppressWarnings("UseSpecificCatch")
public abstract class AbstractProcessFactory implements ProcessFactory {

    private final String id;
    private final String name;
    private final String description;
    private DynStruct inputParametersDefinition;
    private DynStruct outputParametersDefinition;
    private final String groupName;

    protected AbstractProcessFactory(
            String id,
            String name,
            String groupName,
            String description
    ) {
        this.id = id;
        this.name = name;
        this.groupName = groupName;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getGroup() {
        return this.groupName;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    protected void setInputParametersDefinition(DynStruct definition) {
        this.inputParametersDefinition = definition;
    }

    protected void setOutputParametersDefinition(DynStruct definition) {
        this.outputParametersDefinition = definition;
    }

    @Override
    public DynStruct getInputParametersDefinition() {
        return this.inputParametersDefinition;
    }

    @Override
    public DynStruct getOutputParametersDefinition() {
        return this.outputParametersDefinition;
    }

    @Override
    public boolean isApplicableTo(DataStore store) {
        for (DynField field1 : this.inputParametersDefinition.getDynFields()) {
            DynField_v2 field = (DynField_v2) field1;
            if (field.getType() == DataTypes.OBJECT && field.isMandatory()) {
                try {
                    Class classOfvalue = field.getClassOfValue();
                    if (classOfvalue.isInstance(store)) {
                        List<Integer> geomTypes = (List<Integer>) field.getTags()
                                .get(ParametersDefinitionBuilder.GEOMETRY_TYPES);
                        if (geomTypes == null || geomTypes.isEmpty() ) {
                            return true;
                        }
                        FeatureStore featureStore = (FeatureStore) store;
                        FeatureType featureType = featureStore.getDefaultFeatureType();
                        FeatureAttributeDescriptor attrdesc = featureType.getDefaultGeometryAttribute();
                        if( attrdesc == null ) {
                            return false; // No tiene geometria
                        }
                        GeometryType geomType = attrdesc.getGeomType();
                        if( geomType == null ) {
                            return false;
                        }
                        for (Integer geomType1 : geomTypes) {
                            if (geomType.isTypeOf(geomType1)) {
                                return true;
                            }
                        }
                    }
                    return false;
                } catch (Exception ex) {
                    return false;
                }
            }
        }
        return true;
    }

}
