package org.gvsig.processbox.lib.impl;

import java.io.File;
import org.gvsig.processbox.lib.api.ProcessBoxUtils;
import org.gvsig.processbox.lib.api.ProcessBuilder;

/**
 *
 * @author jjdelcerro
 */
public class Pruebas {
    
    
    private void test1() {
        ProcessBuilder builder = ProcessBoxUtils.processBuilder("XYShift");
        builder
            .input()
                .set("X", 100)
                .set("Y", 100)
                .set("layer", new File("/tmp/pp.shp"))
            .output()
                .set("RESULT_POL", new File("/tmp/pp_pol.shp"))
                .set("RESULT_LINE", new File("/tmp/pp_line.shp"))
                .set("RESULT_POINT", new File("/tmp/pp_point.shp"))
            ;
        builder.input().params().getAsFeatureStoreInputChannel("layer").setUseSelection(true);
        builder.input().params().getAsFeatureStoreInputChannel("layer").setFilter("nmnmnbmn");
        builder.execute();
    }

    private void test2() throws Exception {
        ProcessBoxUtils.processBuilder("XYShift")
            .input(
                    "X", 100, 
                    "Y", 100, 
                    "layer", new File("/tmp/pp.shp"), 
                    "USE_SELECTION", false
            )
            .output(
                    "RESULT_POL", new File("/tmp/pp_pol.shp"), 
                    "RESULT_LINE", new File("/tmp/pp_line.shp"), 
                    "RESULT_POINT", new File("/tmp/pp_point.shp")
            )
            .execute()
            .output().params().getAsFeatureStoreOutputChannel("RESULT_POINT")
                .getStore().getFeatureCount();
        
        /*
        En el ExpressionEvaluator se podria utilizar algo como:
        
        PROCESS XYShift 
            INPUTS 
                X = 100, 
                Y = 100, 
                LAYER = FILE('/tmp/pp.shp')
            OUTPUT 
                RESULT_POL = FILE('/tmp/pp_pol.shp'), 
                RESULT_LINE = FILE('/tmp/pp_line.shp'), 
                RESULT_POINT = FILE('/tmp/pp_point.shp')
            RETURN 
                LEN(RESULT_POL)
        */
    }
}
