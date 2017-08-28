package models.db.utils;

import javax.security.auth.login.Configuration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DbSettingsProperties {
    private Properties prop;
    private static final String DIRECTORY = System.getProperty("user.home") + "\\" + "Documents\\Aile Ustasi";
//    private final String DIRECTORY = "C:\\Users\\User\\Documents";
    private final String CONFIG_PATH = "dbsettings.properties";
    public static final String HOST = "HOST";
    public static final String PORT = "PORT";
    public static final String DB_NAME="DB_NAME";

    {System.out.println(System.getProperty("user.home"));}
    public static DbSettingsProperties instance = null;

    private DbSettingsProperties(){
        prop = new Properties();
    }

    public static DbSettingsProperties getInstance(){
        if(instance == null){
            instance = new DbSettingsProperties();
        }
        return instance;
    }


    public void saveProperty(String key, String value){
        prop.setProperty(key,value);
        try {
            prop.store(new FileOutputStream(getConfigFile()),null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        try {
            prop.load(new FileInputStream(getConfigFile()));
            return prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }


    private File getConfigFile(){
        File dir = new File(DIRECTORY);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(DIRECTORY + "\\" + CONFIG_PATH);
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return file;
    }

}
