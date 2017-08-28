package models.db.tables;

import models.db.beans.Operator;
import models.db.beans.User;
import models.db.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    private static Connection conn;
    public static User getRow(String username) throws SQLException {
        conn = ConnectionManager.getInstance().getConnection();
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;
        ResultSet rs = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if(rs.next()){
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setId(rs.getInt("id"));
                user.setUserType(rs.getString("user_type"));
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
        return user;
    }

}
