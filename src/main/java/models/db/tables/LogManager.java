package models.db.tables;

import models.db.beans.Log;
import models.db.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

public class LogManager
{
    private static Connection conn = ConnectionManager.getInstance().getConnection();


    public static List<Log> getAll(){
        List<Log> logs = null;
        String sql = "SELECT * FROM logs";
        try(
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(sql);
                ){
            if(rs.next()){
                logs = new ArrayList<>();
                rs.beforeFirst();
                while (rs.next()){
                    Log log = new Log();
                    log.setId(rs.getInt("id"));
                    log.setCode(rs.getString("code"));
                    log.setMessage(rs.getString("message"));
                    log.setOperatorId(rs.getInt("operator_id"));
                    log.setLogTime(rs.getTimestamp("log_time"));
                    logs.add(log);
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return logs;
    }
}
