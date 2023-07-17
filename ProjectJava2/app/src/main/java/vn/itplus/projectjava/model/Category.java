package vn.itplus.projectjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("avatar")
    String image;

    @SerializedName("name")
    String name;

    public Category() {
    }

    public Category(int id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public Category(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
        return "Category{" +
                "id=" + id +
                ", image=" + image +
                ", name='" + name + '\'' +
                '}';
    }
}
