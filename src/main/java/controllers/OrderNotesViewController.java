package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import models.db.beans.Order;

public class OrderNotesViewController extends Controller{
    private Order order;
    @Override
    public void init() {

    }

    @Override
    public void update() {
        textAreaNote.setText(order.getNote());
    }

    @FXML
    private TextArea textAreaNote;

    public void setOrder(Order order){
        this.order = order;
    }
}
