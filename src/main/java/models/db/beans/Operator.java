package models.db.beans;

public class Operator {
    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private int identificationNo;
    private String contactNo;

    public int getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(int identificationNo) {
        this.identificationNo = identificationNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
