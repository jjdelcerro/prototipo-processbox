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

import org.apache.commons.lang.StringUtils;
import org.gvsig.andami.IconThemeHelper;
import org.gvsig.andami.plugins.Extension;
import org.gvsig.processbox.lib.api.ProcessBoxLocator;
import org.gvsig.processbox.lib.api.ProcessBoxManager;
import org.gvsig.processbox.swing.api.JProcessBox;
import org.gvsig.processbox.swing.api.ProcessBoxSwingLocator;
import org.gvsig.processbox.swing.api.ProcessBoxSwingManager;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.i18n.I18nManager;
import org.gvsig.tools.swing.api.ToolsSwingLocator;
import org.gvsig.tools.swing.api.windowmanager.WindowManager;
import org.gvsig.tools.swing.api.windowmanager.WindowManager_v2;

/**
 *
 * @author jjdelcerro
 */
public class ProcessBoxExtension extends Extension {

    @Override
    public void initialize() {
        
    }

    @Override
    public void postInitialize() {
        ApplicationProcessBoxServices services = new ApplicationProcessBoxServices();
        
        ProcessBoxManager manager = ProcessBoxLocator.getProcessBoxManager();
        manager.setDefaultServices(services);
        
        ProcessBoxSwingManager swingManager = ProcessBoxSwingLocator.getProcessBoxSwingManager();
        swingManager.setDefaultServices(services);
  
        IconThemeHelper.registerIcon("processbox", "processbox-tree-folder", this);
        IconThemeHelper.registerIcon("processbox", "processbox-tree-project", this);
    }

    
    @Override
    public void execute(String action) {
        if( StringUtils.equalsIgnoreCase("tools-processbox-show", action) ) {
            I18nManager i18n = ToolsLocator.getI18nManager();
            ProcessBoxSwingManager swingManager = ProcessBoxSwingLocator.getProcessBoxSwingManager();
            WindowManager_v2 winManager = (WindowManager_v2) ToolsSwingLocator.getWindowManager();
            
            JProcessBox processBox = swingManager.createJProcessBox();
            winManager.showWindow(
                    processBox.asJComponent(),
                    i18n.getTranslation("_ProcessBox"),
                    WindowManager.MODE.TOOL
            );
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isVisible() {
        return true;
    }
    
}
