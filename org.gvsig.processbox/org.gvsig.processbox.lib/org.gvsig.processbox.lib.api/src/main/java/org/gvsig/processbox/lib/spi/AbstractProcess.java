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

import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.fmap.dal.feature.FeatureSet;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.processbox.lib.api.ProcessFactory;
import org.gvsig.processbox.lib.api.Process;
import org.gvsig.processbox.lib.api.channel.FeatureStoreInputChannel;
import org.gvsig.tools.dispose.DisposeUtils;
import org.gvsig.tools.exception.BaseException;
import org.gvsig.tools.task.AbstractMonitorableTask;
import org.gvsig.tools.task.SimpleTaskStatus;
import org.gvsig.tools.visitor.VisitCanceledException;
import org.gvsig.tools.visitor.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jjdelcerro
 */
public abstract class AbstractProcess 
        extends AbstractMonitorableTask
        implements Process 
    {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcess.class);

    private final ProcessFactory factory;
    
    protected AbstractProcess(ProcessFactory factory) {
        super(factory.getName());
        this.factory = factory;
    }


    @Override
    public ProcessFactory getFactory() {
        return this.factory;
    }

    @Override
    public String getID() {
        return this.getFactory().getID();
    }
    
    protected void processFeatures(FeatureStoreInputChannel inChannel, Visitor visitor) {
        SimpleTaskStatus status = this.getSimpleTaskStatus();
        if( status.isCancellationRequested() ) {
            status.cancel();
            return;
        }
        FeatureStore store = inChannel.getStore();
        FeatureSet set = null;
        try {
            set = store.getFeatureSet();
            status.setRangeOfValues(0, set.getSize());
            status.setCurValue(0);
            for (Feature feature : set) {
                if( status.isCancellationRequested() ) {
                    status.cancel();
                    break;
                }
                visitor.visit(feature);
                status.incrementCurrentValue();
            }
        } catch(Exception ex) {
            // FIXME: Falta tratar el error
            status.abort();
            
        } finally {
            if( status.isRunning() ) {
                status.terminate();
            }
            DisposeUtils.disposeQuietly(set);
        }
    }
    
    protected void processFeatures(FeatureStoreInputChannel inChannel) {
        this.processFeatures(inChannel, new Visitor() {
            @Override
            public void visit(Object obj) throws VisitCanceledException, BaseException {
                processFeature((Feature)obj);
            }
        });
    }
    
    protected void processFeature(Feature feature) {
        throw new UnsupportedOperationException("Need overwrite this method to use it.");
    }



}
