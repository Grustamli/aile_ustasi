package models.db.filtering;

public class FilterByService implements Filter {

    private static final String COLUMN_NAME = "service_id";
    private int value;


    public void setValue(Object o) {
        value = (int) o;

    }

    public String toString() {
        return COLUMN_NAME + " = " + value;
    }
}
