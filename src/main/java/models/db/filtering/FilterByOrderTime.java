package models.db.filtering;

import java.sql.Timestamp;

public class FilterByOrderTime implements Filter{
    private Timestamp timestamp;
    private static String columnName = "order_time";
    @Override
    public void setValue(Object o) {
        timestamp = (Timestamp) o;
    }

    public String toString(){
        return null;
    }
}
