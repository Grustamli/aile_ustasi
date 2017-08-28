package test;

import models.db.beans.Service;
import models.db.filtering.Filter;
import models.db.filtering.FilterManager;
import models.db.tables.ServiceManager;
import models.db.utils.ConnectionManager;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String args[]) throws NoSuchAlgorithmException {
        Filter filter = FilterManager.getFilter(FilterManager.FILTER_BY_SERVICE);
        filter.setValue(5);
        Filter filter2 = FilterManager.getFilter(FilterManager.FILTER_BY_STATUS);
        filter2 .setValue("finished");

        List<Filter> filters = new ArrayList<>();
        filters.add(filter);
        filters.add(filter2);

        System.out.println(FilterManager.combine(filters));

        ConnectionManager.getInstance().setDb("localhost", "5432", "postgres");
        ConnectionManager.getInstance().setCredentials("snoop", "snoop123");
        Connection conn = ConnectionManager.getInstance().getConnection();

//        List<Service> services = ServiceManager.getAll();
//        if(services == null) {
//            System.out.println("is null");
//        }
//
//
//
//        for(Service service : ServiceManager.getAll()){
//            System.out.println(service.getName());
//        }
//
//        String text = "hey";
//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
//        System.out.println(String.valueOf(hash));
//        System.out.println(DigestUtils.sha256Hex(text));
    }
}
