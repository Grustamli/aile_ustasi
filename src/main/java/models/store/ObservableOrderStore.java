package models.store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.db.beans.Order;

import java.util.ArrayList;
import java.util.List;

public class ObservableOrderStore {
    private static ObservableOrderStore instance = null;
    private ObservableList<Order> orders = null;

    private ObservableOrderStore(){
        orders = FXCollections.observableArrayList();
    }

    public static ObservableOrderStore getInstance(){
        if(instance == null){
            instance = new ObservableOrderStore();
        }
        return instance;
    }


    public ObservableList<Order> getList(){
        return orders;
    }

    public void add(Order order){
        orders.add(order);
    }

    public void addAll(List<Order> orders){
        this.orders.addAll(orders);
    }

    public Order get(int id){
        Order order = null;
        for(Order o: orders){
            if(o.getId() == id){
                order = o;
            }
        }
        return order;
    }



    public void clear(){
        orders.clear();
    }
}
