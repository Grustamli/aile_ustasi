package models.db.tables;


import models.db.beans.Operator;
import models.db.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperatorManager {
    private static Connection conn = ConnectionManager.getInstance().getConnection();


    public static List<Operator> getAll(){
        List<Operator> operators = new ArrayList<>();
        String sql = "SELECT * FROM operators";
        try(
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                ){
            while (rs.next()){
                Operator operator = new Operator();
                operator.setId(rs.getInt("id"));
                operator.setUsername(rs.getString("username"));
                operator.setFirstname(rs.getString("firstname"));
                operator.setLastname(rs.getString("lastname"));
                operator.setContactNo(rs.getString("contact_no"));
                operator.setIdentificationNo(rs.getInt("identification_no"));
                operators.add(operator);
            }
        }
        catch (SQLException e){

        }
        return operators;
    }


    public static Operator getRow(int id) throws SQLException {
        String sql = "SELECT * FROM operators WHERE id = ?";
        Operator operator = null;
        ResultSet rs = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql);
                ){
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()){
                operator = new Operator();
                operator.setUsername(rs.getString("username"));
                operator.setId(rs.getInt("id"));
                operator.setFirstname(rs.getString("firstname"));
                operator.setLastname(rs.getString("lastname"));
                operator.setContactNo(rs.getString("contact_no"));
                operator.setIdentificationNo(rs.getInt("identification_no"));

            }
        }
        catch (SQLException e){
        }
        finally {
            if(rs != null){
                rs.close();
            }
        }
        return operator;
    }

    public static Operator getRow(String username) throws SQLException {
        String sql = "SELECT * FROM operators WHERE username = ?";
        Operator operator = null;
        ResultSet rs = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if(rs.next()){
                operator = new Operator();
                operator.setUsername(rs.getString("username"));
                operator.setId(rs.getInt("id"));
                operator.setFirstname(rs.getString("firstname"));
                operator.setLastname(rs.getString("lastname"));
                operator.setContactNo(rs.getString("contact_no"));
                operator.setIdentificationNo(rs.getInt("identification_no"));
            }
        }
        catch (SQLException e){
        }
        finally {
            if(rs != null){
                rs.close();
            }
        }
        return operator;
    }




    public static boolean insert(Operator bean) throws SQLException {
        String sql = "INSERT INTO operators(username, firstname, lastname, contact_no, identification_no) VALUES(?,?,?,?,?)";
        boolean ret = false;
        ResultSet keys = null;

        try(
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ){
            stmt.setString(1,bean.getUsername());
            stmt.setString(2, bean.getFirstname());
            stmt.setString(3, bean.getLastname());
            stmt.setString(4, bean.getContactNo());
            stmt.setInt(5, bean.getIdentificationNo());
            int affected = stmt.executeUpdate();

            if(affected == 1){
                keys = stmt.getGeneratedKeys();
                keys.next();
                bean.setId(keys.getInt("id"));
                ret = true;
            }

        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        finally {
            if(keys != null){
                keys.close();
            }
        }
        return ret;
    }



}
