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
import models.store.ObservableOrderStore;


import java.sql.SQLException;
import java.util.List;

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
    private TextField priceField;

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
        String priceStr = priceField.getText();
        Service service = serviceComboBox.getValue();
        Order order = null;

        if(service == null){
            appInstance.showAlert(Alert.AlertType.ERROR, "Xidməti seçin!");
        }
        else if(fName.equals("")){
            appInstance.showAlert(Alert.AlertType.ERROR, "Adı daxil edin!");
        }
        else if(lName.equals("")){
            appInstance.showAlert(Alert.AlertType.ERROR, "Soyadı daxil edin!");
        }
        else if(contactNo.equals("")){
            appInstance.showAlert(Alert.AlertType.ERROR, "Əlaqə nömrəsini daxil edin!");
        }
        else if(address.equals("")){
            appInstance.showAlert(Alert.AlertType.ERROR, "Ünvanı nömrəsini daxil edin!");
        }
        else if(priceStr.equals("")){
            appInstance.showAlert(Alert.AlertType.ERROR, "Məbləği daxil edin!");
        }
        try{
            double price = Double.parseDouble(priceStr);
            order = new Order();
            order.setFirstname(fName);
            order.setLastname(lName);
            order.setContactNo(contactNo);
            order.setAddress(address);
            order.setNote(notes);
            order.setServiceId(service.getId());
            order.setPrice(price);
            order.setOperatorId(Account.getInstance().getUserId());
            try {
                OrderManager.insert(order);
                ObservableOrderStore.getInstance().add(order);
                appInstance.closeNewOrderStage();
        } catch (SQLException e) {
            appInstance.showAlert(Alert.AlertType.ERROR, "Serverdə səhvlik yarandı. Sifariş yerləşdirilə bilmədi.");
        }
        }
        catch (Exception e){
            appInstance.showAlert(Alert.AlertType.ERROR, "Məbləği doğru daxil edin!");
        }




//        try {
//            OrderManager.insert(order);
//            ControllerStore.getInstance().get(ControllerName.HOME_VIEW).update();
//            appInstance.closeNewOrderStage();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getErrorCode());
//        }
    }

    @FXML
    private void handleCancelButtonClicked(){
        appInstance.closeNewOrderStage();
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
                            setText(item.getName() + " - " + item.getHourlyPrice() + " AZN");
                        }
                    }
                };
            }
        };


        serviceComboBox.setButtonCell((ListCell<Service>) cellFactory.call(null));
        serviceComboBox.setCellFactory(cellFactory);
    }



}
