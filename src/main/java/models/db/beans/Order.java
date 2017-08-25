package models.db.beans;

import javafx.beans.property.*;

import java.sql.Timestamp;

public class Order {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty firstname = new SimpleStringProperty();
    private StringProperty lastname = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty contactNo = new SimpleStringProperty();
    private IntegerProperty serviceId = new SimpleIntegerProperty();
    private StringProperty serviceName = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private IntegerProperty operatorId = new SimpleIntegerProperty();
    private StringProperty operatorName = new SimpleStringProperty();
    private ObjectProperty<Timestamp> orderTime = new SimpleObjectProperty<>();
    private StringProperty status = new SimpleStringProperty();
    private StringProperty note = new SimpleStringProperty();

    public Order(){};

    public int getId() {
        return id.get();
    }


    /*************** Setters ***********************/

    public void setId(int id) {
        this.id.set(id);
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setContactNo(String contactNo) {
        this.contactNo.set(contactNo);
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime.set(orderTime);
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setOperatorId(int operatorId) {
        this.operatorId.set(operatorId);
    }

    public void setServiceId(int serviceId) {
        this.serviceId.set(serviceId);
    }

    public void setServiceName(String serviceName) {
        this.serviceName.set(serviceName);
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public void setOperatorName(String operatorName) {
        this.operatorName.set(operatorName);
    }






    /*************** Property Getters ***********************/

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public StringProperty contactNoProperty() {
        return contactNo;
    }

    public IntegerProperty serviceIdProperty() {
        return serviceId;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty operatorIdProperty() {
        return operatorId;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty noteProperty() {
        return note;
    }

    public StringProperty serviceNameProperty() {
        return serviceName;
    }

    public StringProperty operatorNameProperty() {
        return operatorName;
    }

    public ObjectProperty<Timestamp> orderTimeProperty(){
        return orderTime;
    }



    /*************** Getters ***********************/

    public String getFirstname() {
        return firstname.get();
    }

    public String getLastname() {
        return lastname.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getContactNo() {
        return contactNo.get();
    }

    public Double getPrice() {
        return price.get();
    }

    public Timestamp getOrderTime() {
        return orderTime.get();
    }

    public String getStatus() {
        return status.get();
    }

    public int getServiceId() {
        return serviceId.get();
    }

    public String getServiceName() {
        return serviceName.get();
    }

    public int getOperatorId() {
        return operatorId.get();
    }

    public String getNote() {
        return note.get();
    }

    public String getOperatorName() {
        return operatorName.get();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + String.valueOf(getId()) + "\n");
        sb.append("firstname: " + String.valueOf(getFirstname()) + "\n");
        sb.append("lastname: " + String.valueOf(getLastname()) + "\n");
        sb.append("contact_no: " + String.valueOf(getContactNo()) + "\n");
        sb.append("address: " + String.valueOf(getAddress()) + "\n");
        sb.append("service_id: " + String.valueOf(getServiceId()) + "\n");
        sb.append("price: " + String.valueOf(getPrice()) + "\n");
        sb.append("operator_id: " + String.valueOf(getOperatorId()) + "\n");
        sb.append("order_time: " + String.valueOf(getOrderTime()) + "\n");
        sb.append("status: " + String.valueOf(getStatus()) + "\n");
        sb.append("note: " + String.valueOf(getNote()) + "\n");
        return sb.toString();
    }
}
