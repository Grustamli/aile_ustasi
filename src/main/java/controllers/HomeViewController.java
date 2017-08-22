package controllers;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import models.db.beans.Operator;
import models.db.beans.Order;
import models.db.beans.Service;
import models.db.tables.OperatorManager;
import models.db.tables.OrderManager;
import models.db.tables.ServiceManager;
import models.db.utils.ConnectionManager;
import org.apache.commons.codec.binary.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;


public class HomeViewController extends Controller{
    private ObservableList<Order> items = null;
    private final String OPERATOR_FILTER_1 = "Hamısı";
    private final String OPERATOR_FİLTER_2 = "Özüm";
    private final String STATUS_FILTER_1 = "fəaldır";
    private final String STATUS_FILTER_2 = "tamamlandı";
    private final String STATUS_FILTER_3 = "ləğv olundu";

    @FXML
    private void initialize(){
        columnId.setMaxWidth(50);
        setDisabledAll(true);
    }

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> columnId;

    @FXML
    private TableColumn<Order,String> columnService;

    @FXML
    private TableColumn<Order,String> columnFirstname;

    @FXML
    private TableColumn<Order,String> columnLastname;

    @FXML
    private TableColumn<Order,String> columnTelephone;

    @FXML
    private TableColumn<Order,String> columnAddress;

    @FXML
    private TableColumn<Order,String> columnStatus;

    @FXML
    private TableColumn<Order, Double> columnPrice;

    @FXML
    private TableColumn<Order, Timestamp> columnOrderTime;




    @FXML
    private Button newOrderButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button applyButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button loginButton;





    @FXML
    private ComboBox<String> operatorComboBox;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private ComboBox<Service> serviceComboBox;

    @FXML
    private ComboBox<String> dateComboBox;


    @FXML
    private void handleNewOrderClicked(){
        Connection connection = ConnectionManager.getInstance().getConnection();
        if(connection == null){
            appInstance.showLoginStage();
        }
        else{
            appInstance.showNewOrderStage();
        }
    }


    @FXML
    private void handleOnLoginClicked(){
        appInstance.showLoginStage();
    }

    @FXML
    private void handleSearchButtonClicked(){
        String idStr = searchField.getText();
        try{
            int id = Integer.parseInt(idStr);
            items.clear();
            Order order = OrderManager.getRow(id);
            if(order != null) items.add(order);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void init() {

    }

    @Override
    public void update() {
        if(items == null){
            loadOrderTable();
        }else{
            refreshOrders();
        }
        loadFilterComboboxes();
        setDisabledAll(false);



    }


    private void loadOrderTable(){

        if(items == null) items = FXCollections.observableArrayList(OrderManager.getAll());
        columnId.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getId()).asObject());
        columnService.setCellValueFactory(e -> {
            try {
                return new SimpleStringProperty(ServiceManager.getRow(e.getValue().getServiceId()).getName());
            } catch (SQLException e1) {
                return null;
            }
        });
        columnFirstname.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirstname()));
        columnLastname.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLastname()));
        columnTelephone.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getContactNo()));
        columnAddress.setCellValueFactory(e-> new SimpleStringProperty(e.getValue().getAddress()));
        columnStatus.setCellValueFactory(e-> new SimpleStringProperty(e.getValue().getStatus()));
        columnPrice.setCellValueFactory(e -> {
            try{
                System.out.println(e.getValue().getPrice());
               return new SimpleDoubleProperty(e.getValue().getPrice()).asObject();
            }catch (NullPointerException ex){
                ex.printStackTrace();
                return null;
            }

        });
        columnOrderTime.setCellValueFactory(e -> new SimpleObjectProperty<Timestamp>(e.getValue().getOrderTime()));
        orderTable.setItems(items);
    }

    public void refreshOrders(){
        items.clear();
        items.addAll(OrderManager.getAll());
    }

    public void setFilterDisabled(boolean disabled){
        applyButton.setDisable(disabled);
        resetButton.setDisable(disabled);
        operatorComboBox.setDisable(disabled);
        statusComboBox.setDisable(disabled);
        serviceComboBox.setDisable(disabled);
        dateComboBox.setDisable(disabled);
    }

    public void setSearchDisabled(boolean disabled){
        searchButton.setDisable(disabled);
        searchField.setDisable(disabled);
    }

    public void setDisabledAll(boolean disabled){
        setSearchDisabled(disabled);
        setFilterDisabled(disabled);
        newOrderButton.setDisable(disabled);
        orderTable.setDisable(disabled);
    }

    public void loadOperatorCombobox(){
        ObservableList<String> items = FXCollections.observableArrayList(OPERATOR_FILTER_1,OPERATOR_FİLTER_2);
        operatorComboBox.setItems(items);
    }

    public void loadServiceCombobox(){
        ObservableList<Service> items = FXCollections.observableArrayList(ServiceManager.getAll());
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
                            setText(item.getName());
                        }
                    }
                };
            }
        };


        serviceComboBox.setButtonCell((ListCell<Service>) cellFactory.call(null));
        serviceComboBox.setCellFactory(cellFactory);
    }

    public void loadFilterComboboxes(){
        loadOperatorCombobox();
        loadServiceCombobox();
    }
}
