package vn.itplus.projectjava.model;

public class Cart {
    int id;
    String name;
    int price;
    String image;
    int qty;

    public Cart() {
    }

    public Cart(int id, String name, int price, String image, int qty) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", qty=" + qty +
                '}';
    }
}
