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

import org.gvsig.processbox.lib.api.ProcessFactory;
import org.gvsig.processbox.lib.api.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jjdelcerro
 */
public abstract class AbstractProcess implements Process {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcess.class);

    private final ProcessFactory factory;
    
    protected AbstractProcess(ProcessFactory factory) {
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


}
