package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerStore {
    private Map<ControllerName, Controller> controllerMap = null;

    private static ControllerStore instance = null;

    private ControllerStore(){
        controllerMap = new HashMap<>();
    }

    public static ControllerStore getInstance(){
        if(instance == null) instance = new ControllerStore();
        return instance;
    }

    public void add(ControllerName cName, Controller controller){
        controllerMap.put(cName,controller);
    }

    public Controller get(ControllerName cName){
        return controllerMap.get(cName);
    }

    public void updateAll(){
        for (Controller controller: controllerMap.values()) {
            controller.update();
        }
    }


}
