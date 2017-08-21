package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.db.beans.Order;
import models.db.utils.ConnectionManager;

import java.sql.Connection;


public class HomeViewController extends Controller{

    @FXML
    private void initialize(){
    }

    @FXML
    private TableView<Order> orderTable;

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



    @Override
    public void init() {

    }

    @Override
    public void update() {

    }


}
