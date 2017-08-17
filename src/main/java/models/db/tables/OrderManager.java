package models.db.tables;



import models.db.beans.Operator;
import models.db.beans.Order;
import models.db.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static  Connection conn = ConnectionManager.getInstance().getConnection();

    private OrderManager(){}

    public static List<Order> getAll(){
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try(
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

        ){
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setFirstname(rs.getString("firstname"));
                order.setLastname(rs.getString("lastname"));
                order.setAddress(rs.getString("address"));
                order.setContactNo(rs.getString("contact_no"));
                order.setOperatorId(rs.getInt("operator_id"));
                order.setServiceId(rs.getInt("service_id"));
                order.setOrderTime(rs.getTimestamp("order_time"));
                order.setNote(rs.getString("note"));
                orders.add(order);
            }
        }
        catch (SQLException e){
            System.err.println("SQL error");
        }
        return orders;
    }

    public static boolean insert(Order order) throws SQLException {
        boolean inserted = false;
        String sql = "INSERT INTO orders(firstname, lastname, contact_no, address, status, operator_id, service_id, note)" +
                "VALUES(?,?,?,?,?::status,?,?,?)";
        ResultSet keys = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ){
            stmt.setString(1, order.getFirstname());
            stmt.setString(2, order.getLastname());
            stmt.setString(3, order.getContactNo());
            stmt.setString(4, order.getAddress());
            stmt.setString(5, order.getStatus());
            stmt.setInt(6, order.getOperatorId());
            stmt.setInt(7, order.getServiceId());
            stmt.setString(8, order.getNote());

            int affected = stmt.executeUpdate();
            if(affected == 1){
                keys = stmt.getGeneratedKeys();
                keys.next();
                int newId = keys.getInt("id");
                order.setId(newId);
                inserted = true;
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        finally {
            if(keys != null) keys.close();
        }
        return inserted;
    }

    public static Order getRow(int id) throws SQLException {
        String sql = "SELECT * FROM operators WHERE id = ?";
        ResultSet rs = null;
        Order order = null;
        try (
                PreparedStatement stmt = conn.prepareStatement(sql);

        ){
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if(rs.next()){
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setFirstname(rs.getString("firstname"));
                order.setLastname(rs.getString("lastname"));
                order.setAddress(rs.getString("address"));
                order.setContactNo(rs.getString("contact_no"));
                order.setOperatorId(rs.getInt("operator_id"));
                order.setServiceId(rs.getInt("service_id"));
                order.setOrderTime(rs.getTimestamp("order_time"));
                order.setNote(rs.getString("note"));
            }
        }
        catch (SQLException e){

        }
        finally {
            if(rs != null) rs.close();
        }
        return order;
    }

    public static boolean delete(int id) {
        boolean deleted = false;
        String sql = "DELETE FROM orders WHERE id = ?";
        try(
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ){
            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();
            if(affected == 1){
              deleted = true;
            }
        }
        catch (SQLException e){}
        return  deleted;
    }

    public static boolean update(Order bean) throws SQLException{
        boolean updated = false;
        String sql = "SELECT * FROM orders WHERE id = ?";
        ResultSet rs = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ){
            stmt.setInt(1, bean.getId());
            rs = stmt.executeQuery();
            if(rs.next()){
                rs.updateInt("id", bean.getId());
                rs.updateString("firstname", bean.getFirstname());
                rs.updateString("lastname", bean.getLastname());
                rs.updateString("address", bean.getAddress());
                rs.updateString("contact_no", bean.getContactNo());
                rs.updateString("status", bean.getStatus());
                rs.updateInt("service_id", bean.getServiceId());
                rs.updateDouble("price", bean.getPrice());
                rs.updateString("note", bean.getNote());
                rs.updateRow();
                updated = true;
            }
        }
        catch (SQLException e){

        }
        finally {
            if(rs != null) rs.close();
        }
        return  updated;
    }

    public static List<Order> filter(String filter) throws SQLException {
        String sql = "SELECT * FROM orders WHERE ?";
        ResultSet rs = null;
        List<Order> orders = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ){
            stmt.setString(1, filter);
            rs = stmt.executeQuery();
            if(rs.next()){
                rs.beforeFirst();
                orders = new ArrayList<>();
                while (rs.next()){
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setFirstname(rs.getString("firstname"));
                    order.setLastname(rs.getString("lastname"));
                    order.setAddress(rs.getString("address"));
                    order.setContactNo(rs.getString("contact_no"));
                    order.setOperatorId(rs.getInt("operator_id"));
                    order.setServiceId(rs.getInt("service_id"));
                    order.setOrderTime(rs.getTimestamp("order_time"));
                    order.setNote(rs.getString("note"));
                }
            }
        }
        catch (SQLException e){
        }
        finally {
            if(rs != null){
                rs.close();
            }
        }
        return orders;
    }

}


