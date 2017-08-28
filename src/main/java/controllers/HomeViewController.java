package controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import models.db.beans.Order;
import models.db.beans.Service;
import models.db.filtering.Filter;
import models.db.filtering.FilterManager;
import models.db.tables.OrderManager;
import models.db.tables.ServiceManager;
import models.db.utils.Account;
import models.db.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class HomeViewController extends Controller{
    private ObservableList<Order> items = null;
    private final String OPERATOR_FİLTER_ME = "Özüm";
    private final String FILTER_ALL = "Hamısı";
    private final String STATUS_FILTER_1 = "FƏALDIR";
    private final String STATUS_FILTER_2 = "TAMAMLANDI";
    private final String STATUS_FILTER_3 = "LƏĞV OLUNDU";

    @FXML
    private void initialize(){
        columnId.setMaxWidth(50);
        styleTable();
        setDisabledAll(true);



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
        setDisabledAll(false);
        styleTable();

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
    private MenuItem menuDBSettings;


    @FXML
    private Button newOrderButton;

    @FXML
    private Button resetButton;

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
    private ComboBox<String> serviceComboBox;

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
    private void handleSearchKeyTyped(){
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


    @FXML
    private void handleFilterClicked() throws SQLException {
        String operatorChoice = operatorComboBox.getValue();
        String serviceChoice = serviceComboBox.getValue();
        String statusChoice = statusComboBox.getValue();

        List<Filter> filters = new ArrayList<>();
        if(operatorChoice !=null && operatorChoice.equals(OPERATOR_FİLTER_ME)){
            Filter filter = FilterManager.getFilter(FilterManager.FILTER_BY_OPERATOR);
            filter.setValue(Account.getInstance().getUserId());
            filters.add(filter);
        }
        if(serviceChoice != null && !serviceChoice.equals(FILTER_ALL)){
            Filter filter = FilterManager.getFilter(FilterManager.FILTER_BY_SERVICE);
            int serviceId = ServiceManager.getRow(serviceChoice).getId();
            filter.setValue(serviceId);
            filters.add(filter);
        }
        if(statusChoice != null && !statusChoice.equals(FILTER_ALL)){
            Filter filter = FilterManager.getFilter(FilterManager.FILTER_BY_STATUS);
            filter.setValue("'" + statusChoice + "'");
            filters.add(filter);
        }
        if(!filters.isEmpty()){
            System.out.println(FilterManager.combine(filters));
            items.clear();
            items.addAll(OrderManager.filter(FilterManager.combine(filters)));
        }
        else {
            refreshOrders();
        }

    }

    @FXML
    private void handleOnResetClicked(){
        operatorComboBox.setValue(null);
        serviceComboBox.setValue(null);
        statusComboBox.setValue(null);
        refreshOrders();
    }



    @FXML
    private void handleDBSettingsClicked(){
        appInstance.showDBSettingsPane();
    }

//    private void recursiveUserTypeCheck(){
//        if(Account.getInstance().getUserType() == null){
//            appInstance.showLoginStage();
//            recursiveUserTypeCheck();
//
//        }
//        else if(Account.getInstance().getUserType().equals(Account.TYPE_OPERATOR)){
//            appInstance.showAlert(Alert.AlertType.ERROR, "Yalnız admin bu funksiyaya daxil ola bilər");
//        }
//        else{
//            appInstance.showDBSettingsPane();
//
//        }
//    }




    private void loadOrderTable(){
        for(Order order : OrderManager.getAll()){
            System.out.println(order.toString());
        }

        if(items == null) items = FXCollections.observableArrayList(OrderManager.getAll());
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnService.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        columnFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        columnLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        columnTelephone.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        columnOperator.setCellValueFactory(new PropertyValueFactory<>("operatorName"));
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
        searchField.setDisable(disabled);
    }

    public void setDisabledAll(boolean disabled){
        setSearchDisabled(disabled);
        setFilterDisabled(disabled);
        newOrderButton.setDisable(disabled);
        orderTable.setDisable(disabled);
    }

    public void loadOperatorCombobox(){
        ObservableList<String> items = FXCollections.observableArrayList(FILTER_ALL, OPERATOR_FİLTER_ME);
        operatorComboBox.setItems(items);
    }

    public void loadServiceCombobox(){

        ObservableList<String> items = FXCollections.observableArrayList();
        items.add(FILTER_ALL);
        for(Service service : ServiceManager.getAll()) items.add(service.getName());
        serviceComboBox.setItems(items);
    }

    private void loadStatusCombobox(){
        statusComboBox.setItems(FXCollections.observableArrayList(FILTER_ALL,STATUS_FILTER_1,STATUS_FILTER_2,STATUS_FILTER_3));
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
//        setStatusColumnColored();
        for(TableColumn column : orderTable.getColumns()){
            column.setStyle("-fx-alignment: CENTER");
        }

    }

    private void setStatusColumnColored(){
        System.out.println("coloring started");
        columnStatus.setCellFactory(column -> {
            return new TableCell<Order,String>(){
                protected void updateItem(String item, boolean empty){
                    super.updateItem(item,empty);
                    if (item == null || empty) {
                        setText(null);
//                        setStyle("");
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
            MenuItem viewNotes = new MenuItem("Qeydləri göstər");


            contextMenu.setOnShown(e -> {
                if(row.getItem() != null && row.getItem().getOperatorId() != Account.getInstance().getUserId()){
                editMenuItem.setDisable(true);
                removeMenuItem.setDisable(true);
            }
            });

            removeMenuItem.setOnAction(e -> {
                boolean isOwn = row.getItem().getId() == Account.getInstance().getUserId();
                boolean deleted = OrderManager.delete(row.getItem().getId());
                if(deleted) orderTable.getItems().remove(row.getItem());
            });

            editMenuItem.setOnAction(e -> {
                appInstance.showEditOrderStage();
                EditOrderViewController controller = (EditOrderViewController) ControllerStore.getInstance().get(ControllerName.EDIT_ORDER);
                controller.setOrder(row.getItem());
                controller.update();
            });

            viewMenuItem.setOnAction(e -> {
                appInstance.showOrderDetailView();
                OrderDetailViewController controller = (OrderDetailViewController) ControllerStore.getInstance().get(ControllerName.ORDER_VIEW);
                controller.setOrder(row.getItem());
                controller.init();
            });

            viewNotes.setOnAction(e -> {
                appInstance.showOrderNoteView();
                OrderNotesViewController controller = (OrderNotesViewController) ControllerStore.getInstance().get(ControllerName.NOTE_VIEW);
                controller.setOrder(row.getItem());
                controller.update();
            });

            contextMenu.getItems().addAll(viewMenuItem, editMenuItem, viewNotes);
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
