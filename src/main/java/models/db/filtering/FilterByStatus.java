package models.db.filtering;

public class FilterByStatus implements Filter {
    private String status;
    private static String columnName = "status";
    @Override
    public void setValue(Object o) {
        status = (String) o;
    }

    public String toString(){
        return columnName + " = " + status;
    }

}
