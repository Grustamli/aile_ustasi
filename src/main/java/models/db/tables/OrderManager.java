package models.db.tables;



import models.db.beans.Operator;
import models.db.beans.Order;
import models.db.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static  Connection conn;

    private OrderManager(){}

    public static List<Order> getAll(){
        conn = ConnectionManager.getInstance().getConnection();
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT orders.id AS id, firstname, lastname, contact_no, address, note, status, price, service_id, order_time, operator_id, services.name AS service_name, username  \n" +
                "FROM orders \n" +
                "JOIN services ON orders.service_id = services.id \n" +
                "JOIN users ON orders.operator_id = users.id \n" +
                "\n ORDER BY id ASC";
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
                order.setStatus(rs.getString("status"));
                order.setNote(rs.getString("note"));
                order.setServiceName(rs.getString("service_name"));
                order.setOperatorName(rs.getString("username"));
                orders.add(order);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.err.println("SQL error");
        }
        return orders;
    }

    public static boolean insert(Order order) throws SQLException {
        conn = ConnectionManager.getInstance().getConnection();
        boolean inserted = false;
        String sql = "INSERT INTO orders(firstname, lastname, contact_no, address, operator_id, service_id, note)" +
                "VALUES(?,?,?,?,?,?,?)";
        ResultSet keys = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ){
            stmt.setString(1, order.getFirstname());
            stmt.setString(2, order.getLastname());
            stmt.setString(3, order.getContactNo());
            stmt.setString(4, order.getAddress());
            stmt.setInt(5, order.getOperatorId());
            stmt.setInt(6, order.getServiceId());
            stmt.setString(7, order.getNote());


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
        conn = ConnectionManager.getInstance().getConnection();
        String sql = "SELECT orders.id AS id, firstname, lastname, contact_no, address, note, status, price, service_id, order_time, operator_id, services.name AS service_name, username  \n" +
                "FROM orders \n" +
                "JOIN services ON orders.service_id = services.id \n" +
                "JOIN users ON orders.operator_id = users.id\n" +
                "\n WHERE orders.id = ?";
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
                order.setServiceName(rs.getString("service_name"));
                order.setOrderTime(rs.getTimestamp("order_time"));
                order.setStatus(rs.getString("status"));
                order.setNote(rs.getString("note"));
                order.setOperatorName(rs.getString("username"));

            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally {
            if(rs != null) rs.close();
        }
        return order;
    }

    public static boolean delete(int id) {
        conn = ConnectionManager.getInstance().getConnection();
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
        conn = ConnectionManager.getInstance().getConnection();
        boolean updated = false;
        String sql = "UPDATE orders SET (firstname, lastname, address, contact_no, status, service_id, price, note) " +
                "= (?,?,?,?,?::order_status,?,?,?) WHERE id = ? ";
        ResultSet rs = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql);
                ){
            stmt.setString(1, bean.getFirstname());
            stmt.setString(2, bean.getLastname());
            stmt.setString(3, bean.getAddress());
            stmt.setString(4, bean.getContactNo());
            stmt.setString(5, bean.getStatus());
            stmt.setInt(6, bean.getServiceId());
            if(bean.getPrice() != null){
                stmt.setDouble(7, bean.getPrice());
            }
            else{
                stmt.setNull(7,Types.FLOAT);
            }
            stmt.setString(8, bean.getNote());
            stmt.setInt(9, bean.getId());
            int affected = stmt.executeUpdate();
            if(affected == 1){
                updated = true;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally {
            if(rs != null) rs.close();
        }
        return  updated;
    }



    public static List<Order> filter(String filter) throws SQLException {
        conn = ConnectionManager.getInstance().getConnection();
        String sql = "SELECT orders.id AS id, firstname, lastname, contact_no, address, note, status, price, service_id, order_time, operator_id, services.name AS service_name, username \n" +
                "FROM orders \n" +
                "JOIN services ON orders.service_id = services.id \n" +
                "JOIN users ON orders.operator_id = users.id \n" +
                "WHERE " + filter;
        System.out.println(filter);
        ResultSet rs = null;
        List<Order> orders = null;
        try(
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ){
            rs = stmt.executeQuery(sql);
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
                    order.setStatus(rs.getString("status"));
                    order.setOrderTime(rs.getTimestamp("order_time"));
                    order.setNote(rs.getString("note"));
                    order.setOperatorName(rs.getString("username"));
                    order.setServiceName(rs.getString("service_name"));
                    orders.add(order);
                }
            }
        }
        catch (SQLException e){
                    System.out.println(e.getMessage());
        }
        finally {
            if(rs != null){
                rs.close();
            }
        }
        return orders;
    }

}


