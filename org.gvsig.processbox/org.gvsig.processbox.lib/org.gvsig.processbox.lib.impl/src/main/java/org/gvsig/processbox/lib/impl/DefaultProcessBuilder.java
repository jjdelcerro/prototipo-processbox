package org.gvsig.processbox.lib.impl;


import org.gvsig.processbox.lib.api.Process;
import org.gvsig.processbox.lib.api.ProcessBoxManager;
import org.gvsig.processbox.lib.api.ProcessBuilder;
import org.gvsig.processbox.lib.api.ProcessParameters;

/**
 *
 * @author jjdelcerro
 */
public class DefaultProcessBuilder implements ProcessBuilder {

    private final ProcessBoxManager manager;
    private final Process process;
    private final DefaultParamatersProcessBuilder input;
    private final DefaultParamatersProcessBuilder output;

    private class DefaultParamatersProcessBuilder implements ParamatersProcessBuilder {

        private final ProcessParameters parameters;

        public DefaultParamatersProcessBuilder(ProcessParameters parameters) {
            this.parameters = parameters;
        }
        
        @Override
        public ParamatersProcessBuilder set(String name, Object value) {
            this.parameters.set(name, value);
            return this;   
        }

        @Override
        public ParamatersProcessBuilder input(Object... values) {
            return DefaultProcessBuilder.this.input(values);
        }

        @Override
        public ParamatersProcessBuilder output(Object... values) {
            return DefaultProcessBuilder.this.output(values);
        }

        @Override
        public Process process() {
            return DefaultProcessBuilder.this.process();
        }

        @Override
        public ProcessParameters params() {
            return this.parameters;
        }
        
        @Override
        public ProcessBuilder execute() {
            return DefaultProcessBuilder.this.execute();
        }

    }
    
    public DefaultProcessBuilder(ProcessBoxManager manager, String processID) {
        this.manager = manager;
        this.process = this.manager.createProcess(processID);
        this.input = new DefaultParamatersProcessBuilder(this.manager.createInputParameters(processID));
        this.output = new DefaultParamatersProcessBuilder(this.manager.createOutputParameters(processID));
    }
    

    @Override
    public ParamatersProcessBuilder input(Object... values) {
        DefaultProcessBoxManager.fillParameters(this.input.params().get(), values);
        return this.input;
    }

    @Override
    public ParamatersProcessBuilder output(Object... values) {
        DefaultProcessBoxManager.fillParameters(this.output.params().get(), values);
        return this.output;
    }

    @Override
    public Process process() {
        return this.process;
    }

    @Override
    public ProcessBuilder execute() {
        this.process.execute(this.input.params(), this.output.params());
        return this;
    }
    
}
