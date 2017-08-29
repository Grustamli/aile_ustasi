package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.db.beans.Order;
import models.db.beans.Service;
import models.db.tables.OrderManager;
import models.db.tables.ServiceManager;

import java.sql.SQLException;
import java.util.List;

public class EditOrderViewController extends Controller{
    private Order order;

    @Override
    public void init() {

    }

    @Override
    public void update() {
        loadServiceChoiceBox();
        loadFieldsWithOrderInfo();
    }

    @FXML
    private void initialize(){

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
    private Button editButton;

    @FXML
    private Button cancelButton;


    @FXML
    private void handleEditButtonClicked(){
        String fName = firstnameField.getText();
        String lName = lastNameField.getText();
        String contactNo = contactNoField.getText();
        String address = addressField.getText();
        String notes = notesField.getText();
        String priceStr = priceField.getText();
        Service service = serviceComboBox.getValue();
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
        else{
            try{
                double price = Double.parseDouble(priceStr);
                order.setServiceId(serviceComboBox.getValue().getId());
                order.setServiceName(serviceComboBox.getValue().getName());
                order.setFirstname(firstnameField.getText());
                order.setLastname(lastNameField.getText());
                order.setContactNo(contactNoField.getText());
                order.setAddress(addressField.getText());
                order.setNote(notesField.getText());
                order.setPrice(price);

                try {
                    OrderManager.update(order);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                appInstance.closeEditOrderStage();
            }
            catch (Exception e){
                appInstance.showAlert(Alert.AlertType.ERROR, "Doğru məbləği daxil edin!");
            }

        }


    }

    @FXML
    private void handleCancelButtonClicked(){
        appInstance.closeEditOrderStage();
    }

    public void setOrder(Order order){
        this.order = order;
        System.out.println(order.getId());
    }

    public void loadFieldsWithOrderInfo(){
        try {
            serviceComboBox.setValue(ServiceManager.getRow(order.getServiceId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        firstnameField.setText(order.getFirstname());
        lastNameField.setText(order.getLastname());
        contactNoField.setText(order.getContactNo());
        addressField.setText(order.getAddress());
        notesField.setText(order.getNote());
        priceField.setText(String.valueOf(order.getPrice()));
    }

    private void loadServiceChoiceBox() {
        List<Service> services = ServiceManager.getAll();
        if (services == null) {
            System.out.println("is null");
        }
        ObservableList<Service> items = FXCollections.observableArrayList(services);
        serviceComboBox.setItems(items);

        StringConverter<Service> stringConverter = new StringConverter<Service>() {
            @Override
            public String toString(Service obj) {
                if (obj == null)
                {
                    return "";
                }
                else
                {
                    return obj.getName() + " - " + obj.getHourlyPrice() + " AZN";
                }
            }

            @Override
            public Service fromString(String string) {
                return null;
            }
        };

        Callback<ListView<Service>, ListCell<Service>> cellFactory = new Callback<ListView<Service>, ListCell<Service>>() {

            @Override
            public ListCell<Service> call(ListView<Service> param) {
                return new ListCell<Service>() {
                    protected void updateItem(Service item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName() + " - " + item.getHourlyPrice() + " AZN");
                        }
                    }
                };
            }
        };
        serviceComboBox.setButtonCell((ListCell<Service>) cellFactory.call(null));
        serviceComboBox.setCellFactory(cellFactory);
        serviceComboBox.setConverter(stringConverter);
    }
}
