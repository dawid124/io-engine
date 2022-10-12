package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class CMDRunnerBlock extends RunnerBlock {

    private static final Logger LOG = LogManager.getLogger( CMDRunnerBlock.class );

    private String cmd;

    public CMDRunnerBlock(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.CMD;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        executeBashCommand(this.cmd);
    }

    private boolean executeBashCommand(String command) {
        boolean success = false;
        Runtime r = Runtime.getRuntime();
        String[] commands = {"bash", "-c", command};
        try {
            Process p = r.exec(commands);

            int i = p.waitFor();
            BufferedReader b = null;
            if (i == 0) {
                b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            } else {
                b = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            }

            b.close();
            success = true;
        } catch (Exception e) {
            LOG.error("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return success;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
