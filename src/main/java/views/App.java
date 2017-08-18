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

    public void start(Stage primaryStage) throws Exception {
        ConnectionManager.getInstance().setDb("localhost", 5432, "postgres");
        ConnectionManager.getInstance().setCredentials("postgres", "Az2104025");
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

    public void showNewOrderStage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/NewOrderView.fxml"));
        try{
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            controller.init();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("YENİ SİFARİŞ");
            stage.initOwner(primaryStage);
            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showLoginStage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/LoginView.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            controller.init();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Daxil olun");
            stage.initOwner(primaryStage);
            stage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public void showAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }


}
