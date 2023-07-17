package vn.itplus.projectjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("userId")
    int userId;

    @SerializedName("address")
    String address;

    @SerializedName("phonenumber")
    String phone;

    @SerializedName("amount")
    int qty;

    @SerializedName("price")
    int price;

    @SerializedName("status")
    int status;

    @SerializedName("orderTime")
    String orderTime;

    public Order() {
    }

    public Order(int id, int userId, String address, String phone, int qty, int price, int status, String orderTime) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.phone = phone;
        this.qty = qty;
        this.price = price;
        this.status = status;
        this.orderTime = orderTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", status=" + status +
                ", orderTime='" + orderTime + '\'' +
                '}';
    }
}
