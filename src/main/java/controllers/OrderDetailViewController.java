package controllers;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.db.beans.Order;

public class OrderDetailViewController extends Controller{
    private Order order;

    @Override
    public void init() {
        loadLabels();
    }

    @Override
    public void update() {

    }

    @FXML
    private Label labelService;

    @FXML
    private Label labelFirstname;

    @FXML
    private Label labelLastname;

    @FXML
    private Label labelContactNo;

    @FXML
    private Label labelAddress;

    @FXML
    private Label labelStatus;

    @FXML
    private Label labelPrice;


    public void setOrder(Order order){
        this.order = order;
    }

    private void loadLabels(){
        labelService.setText(order.getServiceName());
        labelFirstname.setText(order.getFirstname());
        labelLastname.setText(order.getLastname());
        labelContactNo.setText(order.getContactNo());
        labelAddress.setText(order.getAddress());
        labelPrice.setText(String.valueOf(order.getPrice()));
        labelStatus.setText(order.getStatus());
    }
}
