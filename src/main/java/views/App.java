package views;

import controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.db.utils.ConnectionManager;

import java.io.IOException;

public class App extends Application {
    private Stage primaryStage;
    private Stage loginStage;

    public void start(Stage primaryStage) throws Exception {
        ConnectionManager.getInstance().setDb("localhost", 5432, "aile_ustasi_db");
        this.primaryStage = primaryStage;
        initHomeView();

    }

    private void initHomeView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/HomeView.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage showNewOrderStage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/NewOrderView.fxml"));
        Stage stage = null;
        try{
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            controller.init();
            Scene scene = new Scene(pane);
            stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("YENİ SİFARİŞ");
            stage.initOwner(primaryStage);
            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
        return stage;
    }

    public void showLoginStage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/LoginView.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            controller.init();
            Scene scene = new Scene(pane);
            loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.setTitle("Daxil olun");
            loginStage.initOwner(primaryStage);
            loginStage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeLoginStage(){
        loginStage.close();
    }


    public void showAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }





}
