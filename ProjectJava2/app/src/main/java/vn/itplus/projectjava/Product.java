package vn.itplus.projectjava;

public class Product {
    int  price, image;
    String name;

    public Product( int price, int image, String name) {
        this.price = price;
        this.image = image;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", price=" + price +
                ", image=" + image +
                ", name='" + name + '\'' +
                '}';
    }
}
