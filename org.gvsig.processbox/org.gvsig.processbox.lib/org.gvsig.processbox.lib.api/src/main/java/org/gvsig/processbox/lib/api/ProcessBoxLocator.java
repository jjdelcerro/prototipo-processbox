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
package org.gvsig.processbox.lib.api;

import org.gvsig.tools.locator.AbstractLocator;
import org.gvsig.tools.locator.Locator;
import org.gvsig.tools.locator.LocatorException;

@SuppressWarnings("rawtypes")
public class ProcessBoxLocator extends AbstractLocator {

	private static final String LOCATOR_NAME = "ProcessBoxLocator";
	
	public static final String PROCESSBOX_MANAGER_NAME =
			"org.gvsig.processbox.manager";

	private static final String PROCESSBOX_MANAGER_DESCRIPTION =
			"ProcessBox Manager of gvSIG";
	
	private static final ProcessBoxLocator INSTANCE = new ProcessBoxLocator();

	private ProcessBoxLocator() {

	}

	/**
	 * Return the singleton instance.
	 * 
	 * @return the singleton instance
	 */
	public static ProcessBoxLocator getInstance() {
		return INSTANCE;
	}

        @Override
	public String getLocatorName() {
		return LOCATOR_NAME;
	}

	/**
	 * Return a reference to ProcessBoxManager.
	 * 
	 * @return a reference to ProcessBoxManager
	 * @throws LocatorException
	 *             if there is no access to the class or the class cannot be
	 *             instantiated
	 * @see Locator#get(String)
	 */
	public static ProcessBoxManager getProcessBoxManager()
			throws LocatorException {
		return (ProcessBoxManager) getInstance().get(PROCESSBOX_MANAGER_NAME);
	}

	/**
	 * Registers the Class implementing the ProcessBoxManager interface.
	 * 
	 * @param clazz
	 *            implementing the ProcessBoxManager interface
	 */
	public static void registerProcessBoxManager(Class clazz) {
		getInstance().register(PROCESSBOX_MANAGER_NAME,
				PROCESSBOX_MANAGER_DESCRIPTION, clazz);
	}

	public static void registerDefaultProcessBoxManager(Class clazz) {
		getInstance().registerDefault(PROCESSBOX_MANAGER_NAME,
				PROCESSBOX_MANAGER_DESCRIPTION, clazz);
	}


}
