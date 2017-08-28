package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.db.utils.ConnectionManager;
import models.db.utils.DbSettingsProperties;


public class DatabaseSettingsPaneController extends Controller{
    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @FXML
    private void initialize(){
        loadFields();
    }

    @FXML
    private TextField hostField;

    @FXML
    private TextField portField;

    @FXML
    private TextField dbField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;


    @FXML
    private void handleOkButtonClicked(){
        String host = hostField.getText();
        String port = portField.getText();
        String database = dbField.getText();
        DbSettingsProperties.getInstance().saveProperty(DbSettingsProperties.HOST, host);
        DbSettingsProperties.getInstance().saveProperty(DbSettingsProperties.PORT, port);
        DbSettingsProperties.getInstance().saveProperty(DbSettingsProperties.DB_NAME, database);
        if(host != null && port != null && database != null){
            ConnectionManager.getInstance().setDb(host, port, database);
        }
        appInstance.closeDBSettingsStage();
    }

    @FXML
    private void handleCancelButtonClicked(){
        appInstance.closeDBSettingsStage();
    }


    private void loadFields(){
        hostField.setText(DbSettingsProperties.getInstance().getProperty(DbSettingsProperties.HOST));
        portField.setText(DbSettingsProperties.getInstance().getProperty(DbSettingsProperties.PORT));
        dbField.setText(DbSettingsProperties.getInstance().getProperty(DbSettingsProperties.DB_NAME));
    }
}
