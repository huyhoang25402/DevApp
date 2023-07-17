package vn.itplus.projectjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("amount")
    int qty;

    @SerializedName("name_product")
    String name;

    @SerializedName("orderID")
    int orderId;

    @SerializedName("price")
    int price;

    @SerializedName("status")
    int status;

    public OrderDetail() {
    }

    public OrderDetail(int id, int qty, String name, int orderId, int price, int status) {
        this.id = id;
        this.qty = qty;
        this.name = name;
        this.orderId = orderId;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", qty=" + qty +
                ", name='" + name + '\'' +
                ", orderId=" + orderId +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
