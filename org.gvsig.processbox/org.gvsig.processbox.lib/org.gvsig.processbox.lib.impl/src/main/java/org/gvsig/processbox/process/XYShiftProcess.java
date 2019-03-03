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

import java.awt.geom.AffineTransform;
import java.util.function.Predicate;
import org.gvsig.fmap.dal.feature.EditableFeature;
import org.gvsig.fmap.dal.feature.EditableFeatureAttributeDescriptor;
import org.gvsig.fmap.dal.feature.EditableFeatureType;
import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.fmap.dal.feature.FeatureSet;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.dal.feature.FeatureType;
import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.aggregate.MultiCurve;
import org.gvsig.fmap.geom.aggregate.MultiPoint;
import org.gvsig.fmap.geom.aggregate.MultiSurface;
import org.gvsig.fmap.geom.primitive.Curve;
import org.gvsig.fmap.geom.primitive.Point;
import org.gvsig.fmap.geom.primitive.Surface;
import org.gvsig.fmap.geom.type.GeometryType;
import org.gvsig.processbox.lib.api.channel.OutputChannel;
import org.gvsig.processbox.lib.api.ProcessFactory;
import org.gvsig.processbox.lib.api.ProcessParameters;
import org.gvsig.processbox.lib.spi.AbstractProcess;
import static org.gvsig.processbox.process.XYShiftProcessFactory.LAYER;
import static org.gvsig.processbox.process.XYShiftProcessFactory.RESULT_LINE;
import static org.gvsig.processbox.process.XYShiftProcessFactory.RESULT_POINT;
import static org.gvsig.processbox.process.XYShiftProcessFactory.RESULT_POL;
import static org.gvsig.processbox.process.XYShiftProcessFactory.USE_SELECTION;
import static org.gvsig.processbox.process.XYShiftProcessFactory.X;
import static org.gvsig.processbox.process.XYShiftProcessFactory.Y;

/**
 *
 * @author jjdelcerro
 */
@SuppressWarnings("UseSpecificCatch")
public class XYShiftProcess extends AbstractProcess {

    public XYShiftProcess(ProcessFactory factory) {
        super(factory);
    }

    @Override
    public void execute(ProcessParameters in, ProcessParameters out) {
        try {
            if( out.getAsOutputChannel(RESULT_POINT).exists() ||
                    out.getAsOutputChannel(RESULT_LINE).exists() ||
                    out.getAsOutputChannel(RESULT_POL).exists()  ) {
                throw new IllegalArgumentException("Output already exists");
            }
                                
            FeatureStore store = (FeatureStore) in.getAsInputChannel(LAYER).getStore();
            boolean useSelection = in.getAsBoolean(USE_SELECTION);
            double shiftx = in.getAsDouble(X);
            double shifty = in.getAsDouble(Y);

            AffineTransform transform = new AffineTransform();
            transform.translate(shiftx, shifty);
            
            FeatureType featureType = store.getDefaultFeatureType();
            GeometryType geomType = featureType.getDefaultGeometryAttribute().getGeomType();
            if( geomType.isTypeOf(Geometry.TYPES.SURFACE) ||
                    geomType.isTypeOf(Geometry.TYPES.MULTISURFACE)) {
                this.copy(
                        store,
                        useSelection,
                        new Predicate<Feature>() {
                            @Override
                            public boolean test(Feature t) {
                                Geometry g = t.getDefaultGeometry();
                                return g == null || 
                                        g instanceof Surface || 
                                        g instanceof MultiSurface;
                            }
                        },
                        out.getAsOutputChannel(RESULT_POL),
                        Geometry.TYPES.MULTIPOLYGON,
                        transform
                );
            }
            if( geomType.isTypeOf(Geometry.TYPES.CURVE) ||
                    geomType.isTypeOf(Geometry.TYPES.MULTICURVE)) {
                this.copy(
                        store,
                        useSelection,
                        new Predicate<Feature>() {
                            @Override
                            public boolean test(Feature t) {
                                Geometry g = t.getDefaultGeometry();
                                return g == null || 
                                        g instanceof Curve || 
                                        g instanceof MultiCurve;
                            }
                        },
                        out.getAsOutputChannel(RESULT_LINE),
                        Geometry.TYPES.MULTILINE,
                        transform
                );
            }
            if( geomType.isTypeOf(Geometry.TYPES.POINT) ||
                    geomType.isTypeOf(Geometry.TYPES.MULTIPOINT)) {
                this.copy(
                        store,
                        useSelection,
                        new Predicate<Feature>() {
                            @Override
                            public boolean test(Feature t) {
                                Geometry g = t.getDefaultGeometry();
                                return g == null || 
                                        g instanceof Point || 
                                        g instanceof MultiPoint;
                            }
                        },
                        out.getAsOutputChannel(RESULT_POINT),
                        Geometry.TYPES.MULTIPOINT,
                        transform
                );
            }
        } catch (Exception ex) { // TODO: falta gestionar el error

        }
    }


    private void copy(
            FeatureStore inputStore, 
            boolean useSelection, 
            Predicate<Feature> filter, 
            OutputChannel outputChannel,
            int outputGeomType,
            AffineTransform transform
        ) {
        FeatureStore outputStore = null;
        try {
            FeatureSet set;
            if( useSelection ) {
                set = inputStore.getFeatureSelection();
            } else {
                set = inputStore.getFeatureSet();
            }
            FeatureType featureType = inputStore.getDefaultFeatureType();
            EditableFeatureType newType = featureType.getEditable();
            EditableFeatureAttributeDescriptor attrdesc = (EditableFeatureAttributeDescriptor) newType.getDefaultGeometryAttribute();
            attrdesc.setGeometryType(outputGeomType, attrdesc.getGeomType().getSubType());
            
            outputChannel.setFeatureType(newType);
            outputChannel.create();
            outputStore = (FeatureStore) outputChannel.getStore();

            outputStore.edit(FeatureStore.MODE_APPEND);
            for (Feature feature : set) {
                if( feature==null || !filter.test(feature) ) {
                    continue;
                }
                EditableFeature outf = outputStore.createNewFeature(feature);
                Geometry geom = outf.getDefaultGeometry();
                if( geom!=null ) {
                        geom = geom.cloneGeometry();
                        geom.transform(transform);
                        outf.setDefaultGeometry(geom);
                }
                outputStore.insert(outf);
            }
            
            
        } catch (Exception ex) { // TODO: falta gestionar el error
            
        } finally {
            if( outputStore!=null ) {
                if( outputStore.isAppending() ) {
                    try {
                        outputStore.finishEditing();
                    } catch(Exception ex) {
                        throw new RuntimeException("No puedo terminar edicion.",ex);
                    }
                }
                outputStore.dispose();
            }
        }
    }
}
