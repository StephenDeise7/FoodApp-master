package ie.wit.foodapp.models;

public class Food {
    private String userid;
    private String name;
    private String shop;
    private String price;

    public Food() {

    }

    public Food(String userid, String name, String shop, String price)
    {
        this.userid = userid;
        this.name = name;
        this.shop = shop;
        this.price = price;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return name;
    }
    public void setUsername(String username) {
        this.name = username;
    }

    public String getShop() {
        return shop;
    }
    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }


}