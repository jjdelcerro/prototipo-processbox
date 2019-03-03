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

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.gvsig.processbox.lib.api.ParametersDefinitionBuilder;
import org.gvsig.processbox.lib.api.Process;
import org.gvsig.processbox.lib.api.ProcessBoxManager;
import org.gvsig.processbox.lib.api.ProcessBoxServices;
import org.gvsig.processbox.lib.api.ProcessBuilder;
import org.gvsig.processbox.lib.api.ProcessFactory;
import org.gvsig.processbox.lib.api.ProcessParameters;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.dynobject.DynObject;
import org.gvsig.tools.dynobject.DynObjectManager;
import org.gvsig.tools.util.UnmodifiableBasicList;
import org.gvsig.tools.util.UnmodifiableBasicListAdapter;


/**
 *
 * @author jjdelcerro
 */
public class DefaultProcessBoxManager implements ProcessBoxManager {

    private final List<ProcessFactory> factories;
    private ProcessBoxServices services;
    
    public DefaultProcessBoxManager() {
        this.factories = new ArrayList<>();
    }
    
    @Override
    public UnmodifiableBasicList<ProcessFactory> getProcessFactories() {
        return new UnmodifiableBasicListAdapter<>(factories);
    }
    
    @Override
    public ProcessFactory getProcessFactory(String id) {
        if( StringUtils.isBlank(id) ) {
            return null;
        }
        for (ProcessFactory factory : this.factories) {
            if( StringUtils.equalsIgnoreCase(id, factory.getID()) ) {
                return factory;
            }
        }
        return null;
    }
    
    @Override
    public void addProcessFactories(ProcessFactory factory) {
        if( factory==null ) {
            throw new IllegalArgumentException("Process factory can't be null.");
        }
        this.removeProcessFactory(factory);
        this.factories.add(factory);
    }
    
    @Override
    public boolean removeProcessFactory(ProcessFactory factory) {
        if( factory==null ) {
            throw new IllegalArgumentException("Process factory can't be null.");
        }
        for (int i = 0; i < this.factories.size(); i++) {
            ProcessFactory theFactory = this.factories.get(i);
            if( StringUtils.equalsIgnoreCase(theFactory.getID(), factory.getID()) ) {
                this.factories.remove(i);
                return true;
            }
        }
        return false;
    }
  
    @Override
    public void setDefaultServices(ProcessBoxServices services) {
        this.services = services;
    }

    @Override
    public ProcessBoxServices getDefaultServices() {
        return this.services;
    }

    @Override
    public ProcessParameters createInputParameters(String id, Object... values) {
        ProcessFactory factory = this.getProcessFactory(id);
        if( factory == null ) {
            throw new IllegalArgumentException("Can't find process factory '"+id+"'.");
        }
        DynObjectManager manager = ToolsLocator.getDynObjectManager();
        DynObject parameters = manager.createDynObject(factory.getInputParametersDefinition());
        this.fillParameters(parameters, values);
        return new DefaultProcessParameters(parameters);
    }

    @Override
    public ProcessParameters createOutputParameters(String id, Object... values) {
        ProcessFactory factory = this.getProcessFactory(id);
        if( factory == null ) {
            throw new IllegalArgumentException("Can't find process factory '"+id+"'.");
        }
        DynObjectManager manager = ToolsLocator.getDynObjectManager();
        DynObject parameters = manager.createDynObject(factory.getOutputParametersDefinition());
        fillParameters(parameters, values);
        return new DefaultProcessParameters(parameters);
    }
    
    public static void fillParameters(DynObject parameters, Object[] arguments) {
        if( arguments==null || arguments.length==0 ) {
            return;
        }
        if( (arguments.length % 2)!= 0 ) {
            throw new IllegalArgumentException("An even number of arguments is required.");
        }
        for( int i=0; i<arguments.length; i+=2 ) {
            String name = (String) arguments[i];
            Object value = arguments[i+1];
            parameters.setDynValue(name, value);
        }
    }

    @Override
    public Process createProcess(String id) {
        ProcessFactory factory = this.getProcessFactory(id);
        if( factory == null ) {
            return null;
        }
        Process process = factory.createProcess();
        return process;
    }

    @Override
    public void execute(String id, ProcessParameters inputParameters, ProcessParameters outputParameters) {
        Process process = this.createProcess(id);
        if( process==null ) {
            throw new IllegalArgumentException("Can't find process '"+id+"'.");
        }
        process.execute(inputParameters, outputParameters);
    }

    @Override
    public ParametersDefinitionBuilder createParametersBuilder(String processID, String mode) {
        return new DefaultParametersDefinitionBuilder(processID, mode);
    }

    @Override
    public List<String> getGroups() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public UnmodifiableBasicList<ProcessFactory> getProcessFactories(String group) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public ProcessBuilder createProcessBuilder(String processID) {
        DefaultProcessBuilder builder = new DefaultProcessBuilder(this, processID);
        return builder;
    }
    
}
