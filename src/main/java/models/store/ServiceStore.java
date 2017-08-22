package models.store;

import models.db.beans.Service;
import models.db.tables.ServiceManager;

import java.util.List;

public class ServiceStore {
    private List<Service> services = null;
    private static ServiceStore instance = null;

    private ServiceStore(){}

    public static ServiceStore getInstance(){
        if(instance == null){
            instance = new ServiceStore();
        }
        return instance;
    }

    public List<Service> getList(){
        if(services == null){
            update();
        }
        return services;
    }

    public void update(){
        services = ServiceManager.getAll();
    }

//    public Service

}
