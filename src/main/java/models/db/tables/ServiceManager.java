package models.db.tables;


import models.db.beans.Service;
import models.db.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager {
    private static Connection conn;


    public static List<Service> getAll(){
        conn = ConnectionManager.getInstance().getConnection();
        List<Service> services = null;
        String sql = "SELECT * FROM services";

        try(
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(sql);
                ){
            if(rs.next()){
                rs.beforeFirst();
                services = new ArrayList<>();
                while(rs.next()){
                    Service service = new Service();
                    service.setId(rs.getInt("id"));
                    service.setName(rs.getString("name"));
                    service.setHourlyPrice(rs.getDouble("hourly_price"));
                    services.add(service);
                }
            }
        }
        catch (SQLException e){
                    System.out.println(e.getMessage());
        }
        return services;
    }

    public static Service getRow(int id) throws SQLException {
        conn = ConnectionManager.getInstance().getConnection();
        String sql = "SELECT * FROM services WHERE id = ?";
        Service service = null;
        ResultSet rs = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql);
                ){
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if(rs.next()){
                service = new Service();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                service.setHourlyPrice(rs.getDouble("hourly_price"));
            }

        }
        catch (SQLException e){
                    System.out.println(e.getMessage());
                    System.out.println(e.getErrorCode());
                    System.out.println();
        }
        finally {
            if(rs != null) rs.close();
        }
        return service;
    }


    public static Service getRow(String name) throws SQLException {
        conn = ConnectionManager.getInstance().getConnection();
        String sql = "SELECT * FROM services WHERE name = ?";
        Service service = null;
        ResultSet rs = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if(rs.next()){
                service = new Service();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                service.setHourlyPrice(rs.getDouble("hourly_price"));
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
            System.out.println();
        }
        finally {
            if(rs != null) rs.close();
        }
        return service;
    }


    public static boolean insert(Service service) throws SQLException {
        conn = ConnectionManager.getInstance().getConnection();
        boolean ret = false;
        String sql = "INSERT INTO services(name, hourly_price) VALUES(?,?)";
        ResultSet rs = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ){
            stmt.setString(1, service.getName());
            stmt.setDouble(2, service.getHourlyPrice());
            int affected = stmt.executeUpdate();
            if(affected == 1){
                rs = stmt.getGeneratedKeys();
                rs.next();
                service.setId(rs.getInt("id"));
                ret = true;
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getErrorCode());
            System.err.println();
        }
        finally {
            if(rs != null){
                rs.close();
            }
        }
        return  ret;
    }


}
