package models.db.utils;

public class Account {
    private static Account instance = null;
    public static final String TYPE_ADMIN = "admin";
    public static final String TYPE_OPERATOR = "operator";
    private int userId;
    private String username;
    private String userType;

    public static Account getInstance() {
        if (instance == null) {
            instance = new Account();
        }
        return instance;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}