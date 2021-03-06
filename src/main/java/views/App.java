package views;

import controllers.Controller;
import controllers.ControllerName;
import controllers.ControllerStore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.db.utils.ConnectionManager;
import models.db.utils.DbSettingsProperties;

import java.io.IOException;

public class App extends Application {
    private Stage primaryStage;
    private Stage loginStage;
    private Stage newOrderStage;
    private Stage editOrderStage;
    private Stage orderViewStage;
    private Stage noteViewStage;
    private Stage dbSettingsStage;

    public void start(Stage primaryStage) throws Exception {
        String host = DbSettingsProperties.getInstance().getProperty(DbSettingsProperties.HOST);
        String port = DbSettingsProperties.getInstance().getProperty(DbSettingsProperties.PORT);
        String database = DbSettingsProperties.getInstance().getProperty(DbSettingsProperties.DB_NAME);
        if(host != null && port != null && database != null){
            ConnectionManager.getInstance().setDb(host, port, database);
        }
        this.primaryStage = primaryStage;
        initHomeView();

    }

    private void initHomeView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/HomeView.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            ControllerStore.getInstance().add(ControllerName.HOME_VIEW, controller);
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage showNewOrderStage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/NewOrderView.fxml"));

        try{
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            controller.init();
            ControllerStore.getInstance().add(ControllerName.NEW_ORDER, controller);
            Scene scene = new Scene(pane);
            newOrderStage = new Stage();
            newOrderStage.setScene(scene);
            newOrderStage.setTitle("YENİ SİFARİŞ");
            newOrderStage.initOwner(primaryStage);
            newOrderStage.setResizable(false);
            newOrderStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
        return newOrderStage;
    }

    public void showLoginStage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/LoginView.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            controller.init();
            ControllerStore.getInstance().add(ControllerName.LOGIN_VIEW, controller);
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

    public Stage showEditOrderStage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/EditOrderView.fxml"));

        try{
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            controller.init();
            ControllerStore.getInstance().add(ControllerName.EDIT_ORDER, controller);
            Scene scene = new Scene(pane);
            editOrderStage = new Stage();
            editOrderStage.setScene(scene);
            editOrderStage.setTitle("Redaktə et");
            editOrderStage.initOwner(primaryStage);
            editOrderStage.setResizable(false);
            editOrderStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        return editOrderStage;

    }

    public Stage showOrderDetailView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/OrderDetailView.fxml"));

        try{
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            ControllerStore.getInstance().add(ControllerName.ORDER_VIEW, controller);
            Scene scene = new Scene(pane);
            orderViewStage = new Stage();
            orderViewStage.setScene(scene);
            orderViewStage.setTitle("Sifariş");
            orderViewStage.initOwner(primaryStage);
            orderViewStage.setResizable(false);
            orderViewStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        return orderViewStage;
    }

    public Stage showOrderNoteView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/OrderNotesView.fxml"));
        try{
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            ControllerStore.getInstance().add(ControllerName.NOTE_VIEW, controller);
            Scene scene = new Scene(pane);
            noteViewStage = new Stage();
            noteViewStage.setScene(scene);
            noteViewStage.setTitle("Qeydlər");
            noteViewStage.initOwner(primaryStage);
            noteViewStage.setResizable(false);
            noteViewStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return noteViewStage;
    }

    public Stage showDBSettingsPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/DatabaseSettingsPane.fxml"));
        try{
            AnchorPane pane = (AnchorPane) loader.load();
            Controller controller = loader.getController();
            controller.setAppInstance(this);
            ControllerStore.getInstance().add(ControllerName.DB_SETTINGS_PANE, controller);
            Scene scene = new Scene(pane);
            dbSettingsStage = new Stage();
            dbSettingsStage.setScene(scene);
            dbSettingsStage.setTitle("DB Settings");
            dbSettingsStage.initOwner(primaryStage);
            dbSettingsStage.setResizable(false);
            dbSettingsStage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return noteViewStage;
    }

    public void closeLoginStage(){
        loginStage.close();
    }

    public void closeNewOrderStage(){
        newOrderStage.close();
    }

    public void closeEditOrderStage(){
        editOrderStage.close();
    }

    public void closeOrderViewStage(){
        orderViewStage.close();
    }

    public void closeDBSettingsStage(){
        dbSettingsStage.close();
    }




    public void showAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }


    public Stage getPrimaryStage(){
        return primaryStage;
    }




}
