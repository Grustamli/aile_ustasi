package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.db.beans.Operator;
import models.db.tables.OperatorManager;
import models.db.utils.Account;
import models.db.utils.ConnectionManager;
import models.db.utils.ConnectionStatus;

import java.sql.Connection;
import java.sql.SQLException;


public class LoginViewController extends Controller{
    private Stage stage = null;

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }


    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;


    @FXML
    private void handleOkButtonClicked(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        ConnectionManager.getInstance().setCredentials(username, password);
        Connection conn = ConnectionManager.getInstance().getConnection();
        if(conn == null){
            if(ConnectionManager.getInstance().getStatus() == ConnectionStatus.AUTH_ERROR){
                appInstance.showAlert(Alert.AlertType.ERROR, "Yanliş istifadəçi adı və ya parol. Bir daha yoxlayın");
            }
            else{
                appInstance.showAlert(Alert.AlertType.ERROR, "Serverlə əlaqə qurula bilmədi. Bir daha yoxlayın");
            }
        }
        else{
            try {
                Operator operator = OperatorManager.getRow(username);
                Account.getInstance().setUserId(operator.getId());
                Account.getInstance().setFirstname(operator.getFirstname());
                Account.getInstance().setLastname(operator.getLastname());
                Account.getInstance().setUsername(operator.getUsername());
                appInstance.closeLoginStage();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCancelButtonClicked(){
        appInstance.closeLoginStage();
    }
}
