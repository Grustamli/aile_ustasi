package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import models.db.beans.Order;
import models.db.beans.Service;
import models.db.tables.OrderManager;
import models.db.tables.ServiceManager;
import models.db.utils.Account;
import models.db.utils.ConnectionManager;


import java.sql.SQLException;
import java.util.List;
import java.util.Observable;

public class NewOrderViewController extends Controller{


    @Override
    public void init() {
        loadServiceChoiceBox();
    }

    @Override
    public void update() {

    }

    @FXML
    private ComboBox<Service> serviceComboBox;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField contactNoField;

    @FXML
    private TextField addressField;

    @FXML
    private TextArea notesField;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void handleCreateButtonClicked(){

        String fName = firstnameField.getText();
        String lName = lastNameField.getText();
        String contactNo = contactNoField.getText();
        String address = addressField.getText();
        String notes = notesField.getText();
        int serviceId = serviceComboBox.getValue().getId();

        Order order = new Order();
        order.setFirstname(fName);
        order.setLastname(lName);
        order.setContactNo(contactNo);
        order.setAddress(address);
        order.setNote(notes);
        order.setServiceId(serviceId);
        order.setOperatorId(Account.getInstance().getUserId());

        try {
            OrderManager.insert(order);
            ControllerStore.getInstance().get(ControllerName.HOME_VIEW).update();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
    }

    @FXML
    private void handleCancelButtonClicked(){

    }

    private void loadServiceChoiceBox(){
        List<Service> services = ServiceManager.getAll();
        if(services == null){
            System.out.println("is null");
        }
        ObservableList<Service> items = FXCollections.observableArrayList(services);
        serviceComboBox.setItems(items);

        Callback<ListView<Service>, ListCell<Service>> cellFactory = new Callback<ListView<Service>, ListCell<Service>>(){

            @Override
            public ListCell<Service> call(ListView<Service> param) {
                return new ListCell<Service>(){
                    protected void updateItem(Service item, boolean empty){
                        super.updateItem(item, empty);
                        if(item == null || empty){
                            setGraphic(null);
                        }
                        else {
                            setText(item.getName() + " - " + item.getHourlyPrice() + "AZN");
                        }
                    }
                };
            }
        };


        serviceComboBox.setButtonCell((ListCell<Service>) cellFactory.call(null));
        serviceComboBox.setCellFactory(cellFactory);
    }



}
