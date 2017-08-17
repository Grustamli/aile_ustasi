package models.misc;

public class Account {
    private static  Account instance = null;
    private int userId;
    private String username;
    private String firstname;
    private String lastname;

    private Account(){

    }

    public static Account getInstance(){
        if(instance == null){
            instance = new Account();
        }
        return instance;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
