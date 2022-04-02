package pl.webd.dawid124.ioengine.module.action.service;

import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.model.server.ServerUiAction;
import pl.webd.dawid124.ioengine.module.action.model.server.ServerDevice;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.structure.model.LightGroup;
import pl.webd.dawid124.ioengine.module.structure.model.Scene;

import java.util.*;

class ActionDataFactory {

    private final UiAction uiAction;
    private final String ioId;

    private final Map<String, IDevice> devices;
    private final Scene sceneStructure;
    private final SceneState sceneState;

    private ActionDataFactory(String ioId, UiAction uiAction, Map<String, IDevice> devices,
                              Scene sceneStructure, SceneState sceneState) {
        this.ioId = ioId;
        this.uiAction = uiAction;
        this.devices = devices;
        this.sceneStructure = sceneStructure;
        this.sceneState = sceneState;
    }

    static ActionDataFactory init(String ioId, Map<String, IDevice> devices, Scene sceneStructure,
                                  SceneState sceneState) {
        return new ActionDataFactory(ioId,null, devices, sceneStructure, sceneState);
    }

    static ActionDataFactory init(UiAction uiAction, Map<String, IDevice> devices, Scene sceneStructure,
                                         SceneState sceneState) {
        return new ActionDataFactory(uiAction.getIoId(), uiAction, devices, sceneStructure, sceneState);
    }

    ServerUiAction process() {
        ServerDevice serverDevice;

        if (uiAction == null) {
            serverDevice = fromServerState();
        } else if (uiAction.isGroup()) {
            serverDevice = fromGroupUiAction();
        } else {
            serverDevice = fromUiAction();
        }

        return new ServerUiAction(uiAction, serverDevice);
    }

    private ServerDevice fromUiAction() {
        IDevice device = devices.get(ioId);
        DeviceState deviceState = sceneState.getDeviceState().get(ioId);

        ServerDevice parent = fetchParent();

        return ServerDevice.standard(device, deviceState).setParent(parent).build();
    }

    private ServerDevice fromServerState() {
        IDevice device = devices.get(ioId);
        DeviceState deviceState = sceneState.getDeviceState().get(ioId);

        ServerDevice parent = fetchParent();

        return ServerDevice.standard(device, deviceState).setParent(parent).build();
    }


    private ServerDevice fetchParent() {
        Optional<LightGroup> parentStructure = sceneStructure.getGroups().stream()
                .filter(g -> g.getDeviceIds().contains(ioId))
                .findFirst();

        ServerDevice parent = null;
        if (parentStructure.isPresent()) {
            DeviceState parentState = sceneState.getGroupState().get(parentStructure.get().getIoId());

            parent = ServerDevice.group(parentState).build();
        } return parent;
    }

    private ServerDevice fromGroupUiAction() {
        Optional<LightGroup> groupStructureOpt = sceneStructure.getGroups().stream()
                .filter(g -> g.getIoId().equals(ioId))
                .findFirst();

        if (groupStructureOpt.isPresent()) {
            LightGroup groupStructure = groupStructureOpt.get();

            DeviceState state = sceneState.getGroupState().get(groupStructure.getIoId());

            ServerDevice group = ServerDevice.group(state).build();

            ArrayList<ServerDevice> children = new ArrayList<>();

            for (String ioId: groupStructure.getDeviceIds()) {
                IDevice device = devices.get(ioId);
                DeviceState deviceState = sceneState.getDeviceState().get(ioId);

                children.add(ServerDevice.standard(device, deviceState).setParent(group).build());
            }

            group.setChildren(children);

            return group;
        }

        return null;
    }

    public String getIoId() {
        return ioId;
    }
}
