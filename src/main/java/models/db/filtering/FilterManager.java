package models.db.filtering;

import java.util.List;

public class FilterManager {
    public static final int FILTER_BY_SERVICE = 0;
    public static final int FILTER_BY_ORDER_TIME = 1;
    public static final int FILTER_BY_STATUS = 2;
    public static final int FILTER_BY_OPERATOR = 3;

    private FilterManager(){}

    public static Filter getFilter(int filterType){
        switch (filterType){
            case FILTER_BY_SERVICE:
                return new FilterByService();
            case FILTER_BY_ORDER_TIME:
                return new FilterByOrderTime();
            case FILTER_BY_OPERATOR:
                return new FilterByOperator();
            case FILTER_BY_STATUS:
                return new FilterByStatus();
            default:
                return null;
        }
    }

    public static String combine(List<Filter> filters){
        StringBuilder ret = new StringBuilder();
        int c = 0;
        for(Filter filter : filters){
            ret.append(" " + filter.toString() + " ");
            if(c != filters.size() - 1){
                ret.append("AND");
            }
            c ++;
        }
        return ret.toString();
    }

}
