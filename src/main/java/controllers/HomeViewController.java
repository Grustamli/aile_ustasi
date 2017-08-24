package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import models.db.beans.Operator;
import models.db.beans.Order;
import models.db.beans.Service;
import models.db.tables.OperatorManager;
import models.db.tables.OrderManager;
import models.db.tables.ServiceManager;
import models.db.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;


public class HomeViewController extends Controller{
    private ObservableList<Order> items = null;
    private final String OPERATOR_FILTER_1 = "Hamısı";
    private final String OPERATOR_FİLTER_2 = "Özüm";
    private final String STATUS_FILTER_1 = "FƏALDIR";
    private final String STATUS_FILTER_2 = "TAMAMLANDI";
    private final String STATUS_FILTER_3 = "LƏĞV OLUNDU";

    @FXML
    private void initialize(){
        columnId.setMaxWidth(50);
        styleTable();
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
    private TableColumn<Order, String> columnOperator;




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
        System.out.print(idStr);
        if(idStr.equals("")) refreshOrders();
        else{
            try{
                int id = Integer.parseInt(idStr);
                items.clear();
                Order order = OrderManager.getRow(id);
                if(order != null) items.add(order);
            }catch (Exception e){

            }
        }
    }


    @Override
    public void init() {

    }

    @Override
    public void update() {
        if(items == null){
            loadOrderTable();
            setContextMenuOnTableRow();
        }else{
            refreshOrders();
        }
        loadFilterComboboxes();
        setOrderTableEditable();
        setDisabledAll(false);
        styleTable();



    }


    private void loadOrderTable(){
        for(Order order : OrderManager.getAll()){
            System.out.println(order.toString());
        }
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
               return new SimpleDoubleProperty(e.getValue().getPrice()).asObject();
            }catch (NullPointerException ex){
//                ex.printStackTrace();
                return null;
            }

        });
        columnOrderTime.setCellValueFactory(e -> new SimpleObjectProperty<Timestamp>(e.getValue().getOrderTime()));
        columnOperator.setCellValueFactory(e -> {
            try {
                System.out.println(e.getValue().toString());
                Operator operator = OperatorManager.getRow(e.getValue().getOperatorId());
                System.out.println(operator.getUsername());
                return new SimpleStringProperty(OperatorManager.getRow(e.getValue().getOperatorId()).getUsername());
            } catch (SQLException e1) {
                e1.printStackTrace();
                return null;
            }
        });
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

    private void loadStatusCombobox(){
        statusComboBox.setItems(FXCollections.observableArrayList(STATUS_FILTER_1,STATUS_FILTER_2,STATUS_FILTER_3));
    }


    public void loadFilterComboboxes(){
        loadOperatorCombobox();
        loadServiceCombobox();
        loadStatusCombobox();
    }


    /* methods for table editing */


    private void setFirstnameColumnEditable(){
        columnFirstname.setCellFactory(TextFieldTableCell.forTableColumn());
        columnFirstname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Order, String> event) {
                Order order = (Order) event.getTableView().getItems().get(event.getTablePosition().getRow());
                order.setFirstname(event.getNewValue());
                boolean updated = false;
                try {
                    OrderManager.update(order);
                } catch (SQLException e) {
                    System.out.println("Could not update firstname on db");
                }
            }
        });
    }

    private void setLastnameColumnEditable(){
        columnLastname.setCellFactory(TextFieldTableCell.forTableColumn());
        columnLastname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Order, String> event) {
                Order order = (Order) event.getTableView().getItems().get(event.getTablePosition().getRow());
                order.setLastname(event.getNewValue());
                try {
                    OrderManager.update(order);
                } catch (SQLException e) {
                    System.out.println("Could not update lastname on db");
                }

            }
        });
    }

    private void setServiceColumnEditable(){
        ObservableList<String> items = FXCollections.observableArrayList();
        for(Service service : ServiceManager.getAll()){
            items.add(service.getName());
        }
        columnService.setCellFactory(ChoiceBoxTableCell.forTableColumn(items));
        columnService.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Order, String> event) {

                Order order = event.getTableView().getItems().get(event.getTablePosition().getRow());
                System.out.println(order.toString());
                try {
                    int serviceId = ServiceManager.getRow(event.getNewValue()).getId();
                    order.setServiceId(serviceId);
                    OrderManager.update(order);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void setContactNoColumnEditable(){
        columnTelephone.setCellFactory(TextFieldTableCell.forTableColumn());
        columnTelephone.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Order, String> event) {
                Order order = (Order) event.getTableView().getItems().get(event.getTablePosition().getRow());
                order.setContactNo(event.getNewValue());
                try {
                    OrderManager.update(order);
                } catch (SQLException e) {
                    System.out.println("Could not update contact no on db");
                }

            }
        });
    }

    private void setAddressColumnEditable(){
        columnAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAddress.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Order, String> event) {
                Order order = (Order) event.getTableView().getItems().get(event.getTablePosition().getRow());
                order.setAddress(event.getNewValue());
                System.out.println(order.toString());
                try {
                    OrderManager.update(order);
                } catch (SQLException e) {
                    System.out.println("Could not update address on db");
                }

            }
        });
    }

    private void setStatusColumnEditable(){

//        columnStatus.setCellFactory(ChoiceBoxTableCell.forTableColumn(FXCollections.observableArrayList(STATUS_FILTER_1,STATUS_FILTER_2,STATUS_FILTER_3)));
        columnStatus.setCellFactory(col -> {
                ChoiceBoxTableCell<Order,String> choiceBoxTableCell = new ChoiceBoxTableCell();
                choiceBoxTableCell.getItems().addAll(STATUS_FILTER_1,STATUS_FILTER_2,STATUS_FILTER_3);
                return choiceBoxTableCell;
            }

        );
        columnStatus.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Order, String> event) {
                Order order = event.getTableView().getItems().get(event.getTablePosition().getRow());
                order.setStatus(event.getNewValue());
                try {
                    OrderManager.update(order);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void styleTable(){
        for(TableColumn column : orderTable.getColumns()){
            column.setStyle("-fx-alignment: CENTER");
        }
    }

    private void setStatusColumnColored(){
        System.out.println("coloring started");
//        columnStatus.setCellFactory(new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
//            @Override
//            public TableCell<Order, String> call(TableColumn<Order, String> param) {
//                System.out.println("in call method");
//                TableCell cell = new TableCell(){
//                    @Override
//                    public void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : item);
//                        setStyle("-fx-background-color:" + getColor(item));
//                        System.out.println(getColor(item));
//                    }
//
//                    private String getColor(String status){
//                        switch (status){
//                            case STATUS_FILTER_1:
//                                return "#FFEB3B";
//                            case STATUS_FILTER_2:
//                                return "#76FF03";
//                            case STATUS_FILTER_3:
//                                return "#F44336";
//                            default:
//                                return "";
//                        }
//                    }
//                };
//                return cell;
//            }
//        });

        columnStatus.setCellFactory(column -> {
            return new TableCell<Order,String>(){
                protected void updateItem(String item, boolean empty){
                    super.updateItem(item,empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        setStyle("-fx-background-color:" + getColor(item));
                    }

                }

                private String getColor(String status){
                        switch (status){
                            case STATUS_FILTER_1:
                                return "#FFEB3B";
                            case STATUS_FILTER_2:
                                return "#76FF03";
                            case STATUS_FILTER_3:
                                return "#F44336";
                            default:
                                return "";
                        }
                }
            };
        });

    }


    private void setContextMenuOnTableRow(){
        orderTable.setRowFactory(column -> {
            TableRow<Order> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem removeMenuItem = new MenuItem("Sil");
            MenuItem editMenuItem = new MenuItem("Redaktə et");
            MenuItem viewMenuItem = new MenuItem("Göstər");
            removeMenuItem.setOnAction(e -> {
                boolean deleted = OrderManager.delete(row.getItem().getId());
                if(deleted) orderTable.getItems().remove(row.getItem());
            });

            editMenuItem.setOnAction(e -> {

            });

            contextMenu.getItems().add(removeMenuItem);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(contextMenu)
            );
            return row;
        });
    }



    private void setOrderTableEditable(){
        orderTable.setEditable(true);
        setFirstnameColumnEditable();
        setLastnameColumnEditable();
        setServiceColumnEditable();
        setContactNoColumnEditable();
        setAddressColumnEditable();
        setStatusColumnEditable();
    }


}
