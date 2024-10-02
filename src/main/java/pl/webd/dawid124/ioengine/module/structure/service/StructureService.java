package pl.webd.dawid124.ioengine.module.structure.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.IColor;
import pl.webd.dawid124.ioengine.module.action.model.server.ILedChangeData;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.ICondition;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.ConditionVariableRunner;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.IVariableFetcher;
import pl.webd.dawid124.ioengine.module.automation.macro.json.ExpressionIVariableJsonAdapter;
import pl.webd.dawid124.ioengine.module.automation.macro.json.IConditionJsonAdapter;
import pl.webd.dawid124.ioengine.module.automation.macro.json.IVariableFetcherJsonAdapter;
import pl.webd.dawid124.ioengine.module.automation.macro.json.IVariableJsonAdapter;
import pl.webd.dawid124.ioengine.module.device.model.adapter.DeviceStateJsonAdapter;
import pl.webd.dawid124.ioengine.module.device.model.adapter.IColorJsonAdapter;
import pl.webd.dawid124.ioengine.module.device.model.adapter.ILedChangeDataJsonAdapter;
import pl.webd.dawid124.ioengine.module.device.model.adapter.LocalTimeAdapter;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.structure.controller.RightSliderData;
import pl.webd.dawid124.ioengine.module.structure.model.*;
import pl.webd.dawid124.ioengine.utils.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service public class StructureService {

    private final Gson gson;
    private Home home;
    private final AutomationContext context;

    public StructureService(AutomationContext context) {
        this.context = context;
        this.home = new Home();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(DeviceState.class, new DeviceStateJsonAdapter())
                .registerTypeAdapter(IColor.class, new IColorJsonAdapter())
                .registerTypeAdapter(ILedChangeData.class, new ILedChangeDataJsonAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .registerTypeAdapter(IVariable.class, new ExpressionIVariableJsonAdapter(context))
                .registerTypeAdapter(ICondition.class, new IConditionJsonAdapter(context))
                .registerTypeAdapter(IVariableFetcher.class, new IVariableFetcherJsonAdapter())
                .create();
    }

    @PostConstruct
    public void init() {
        try {
            String structure = ResourceUtils.getResourceYamlAsJson("classpath:structure.yaml");
            home = gson.fromJson(structure, Home.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Home fetchStructure() {
        return home;
    }

    public ConditionVariableRunner fetchConditionVariable(String name) {
        return home.getConditionVariables().get(name);
    }

    public Scene fetchScene(String zoneId, String sceneId) {
        return home.getZones().get(zoneId).getScenes().get(sceneId);
    }

    public RightSliderData fetchRightSliderStructure() {
        List<UiButtonGroup> btnGroups = home.getRightBlindGroups().values().stream()
                .map(bg -> new UiButtonGroup(bg, createUiMacroButtons(bg.getButtons())))
                .collect(Collectors.toList());

        return new RightSliderData(btnGroups);
    }

    private List<UiMacroButton> createUiMacroButtons(List<MacroButton> buttons) {
        return buttons.stream()
                .map(macroButton -> {
                    ICondition activeCondition = macroButton.getActiveCondition();
                    boolean active;
                    if (activeCondition != null) {
                        active = activeCondition.test(context, new HashMap<>(), "GLOBAL");
                    } else {
                        active = macroButton.isActive();
                    }
                    return new UiMacroButton(macroButton, active);
                })
                .collect(Collectors.toList());

    }

}
