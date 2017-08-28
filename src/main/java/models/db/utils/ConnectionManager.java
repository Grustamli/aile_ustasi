package models.db.utils;

import javax.swing.table.TableColumn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private String username;
    private String password;
    private String dbUrl;
    private Connection conn = null;
    private static ConnectionManager instance = null;
    private ConnectionStatus status;
    private static final String authErrorCode = "28P01";

    private ConnectionManager(){}

    public static ConnectionManager getInstance(){
        if(instance == null){
            instance = new ConnectionManager();
        }
        return instance;
    }

    public void setCredentials(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void setDb(String host, String port, String database){
        dbUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
    }



    private boolean openConnection(){
        try {
//            conn = DriverManager.getConnection(dbUrl, username, password);
            Properties connProperties = new Properties();
            connProperties.setProperty("user",username);
            connProperties.setProperty("password",password);
            connProperties.setProperty("charset","UTF-8");
            connProperties.setProperty("encoding","UTF-8");
            conn = DriverManager.getConnection(dbUrl, connProperties);
            return true;
        }
        catch (SQLException e){
            if(e.getSQLState().equals(authErrorCode)){
                setConnectionStatus(ConnectionStatus.AUTH_ERROR);
            }
            else{
                setConnectionStatus(ConnectionStatus.SERVER_ERROR);
            }
            return false;
        }
    }

    public Connection getConnection(){
        if (conn == null){
            if(openConnection()){
                System.out.println("Connection Opened");
                return conn;
            }
            else return null;
        }
        else {
            return conn;
        }
    }

    public void close(){
        System.out.println("Closing Connection");
        try {
            conn.close();
            conn = null;
            System.out.println("Connection closed");
        }
        catch (Exception e){
            System.err.println("Connection could not be closed");
        }
    }


    private void setConnectionStatus(ConnectionStatus status){
        this.status = status;
    }



    public ConnectionStatus getStatus() {
        return status;
    }
}
