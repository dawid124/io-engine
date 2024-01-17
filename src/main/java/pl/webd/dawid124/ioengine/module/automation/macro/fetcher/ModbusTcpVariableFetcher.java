package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.IntegerVariable;

import java.util.Map;

public class ModbusTcpVariableFetcher implements IVariableFetcher {

    private int slaveId;
    private int offset;

    public ModbusTcpVariableFetcher(int slaveId, int offset) {
        this.slaveId = slaveId;
        this.offset = offset;
    }

    @Override
    public EVariableFetcherType getFetcherType() {
        return EVariableFetcherType.MODBUS_TCP;
    }

    @Override
    public IVariable fetch(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        int value = context.getModbusTcpService().readRegister(slaveId, offset);
        return new IntegerVariable(value);
    }

    public int getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId = slaveId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
