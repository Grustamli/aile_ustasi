package models.db.filtering;

public class FilterByOperator implements Filter {
    private int operatorId;
    private static String columnName = "operator_id";
    @Override
    public void setValue(Object o) {
        operatorId = (int) o;
    }

    public String toString(){
        return columnName + " = " + operatorId;
    }

}
