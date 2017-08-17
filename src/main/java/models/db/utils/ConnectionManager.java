package models.db.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private String username;
    private String password;
    private String dbUrl;
    private Connection conn = null;
    private static ConnectionManager instance = null;

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

    public void setDb(String host, int port, String database){
        dbUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
    }



    private boolean openConnection(){
        try {
            conn = DriverManager.getConnection(dbUrl, username, password);
            return true;
        }
        catch (SQLException e){
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


}
