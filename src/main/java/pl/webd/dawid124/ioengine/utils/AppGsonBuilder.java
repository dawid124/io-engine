package pl.webd.dawid124.ioengine.utils;

import com.google.gson.Gson;
import pl.webd.dawid124.ioengine.module.action.model.rest.IColor;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.action.model.server.ILedChangeData;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.ICondition;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.IVariableFetcher;
import pl.webd.dawid124.ioengine.module.automation.macro.json.*;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

public class AppGsonBuilder {
    private final Gson gson;

    private AppGsonBuilder(AutomationContext automationContext) {
        this.gson =  new com.google.gson.GsonBuilder()
                .registerTypeAdapter(IVariable.class, new ExpressionIVariableJsonAdapter(automationContext))
                .registerTypeAdapter(IBlock.class, new IBlockJsonAdapter())
                .registerTypeAdapter(IUiAction.class, new ExpressionUIActionJsonAdapter(automationContext))
                .registerTypeAdapter(ILedChangeData.class, new ExpressionLedChangeDataJsonAdapter(automationContext))
                .registerTypeAdapter(IColor.class, new ExpressionColorJsonAdapter(automationContext))
                .registerTypeAdapter(IVariableFetcher.class, new IVariableFetcherJsonAdapter())
                .registerTypeAdapter(ICondition.class, new IConditionJsonAdapter(automationContext))
                .create();
    }

    public static Gson instance(AutomationContext automationContext) {
        return new AppGsonBuilder(automationContext).gson;
    }
}
