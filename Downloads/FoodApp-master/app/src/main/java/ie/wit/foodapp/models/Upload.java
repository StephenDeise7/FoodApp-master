package ie.wit.foodapp.models;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mShop;
    private String mPrice;
    private String mImageUrl;
    private String mKey;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String shop, String price, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        if (shop.trim().equals("")) {
            shop = "No Shop";
        }

        mName = name;
        mShop = shop;
        mPrice = price;
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public String getShop() {
        return mShop;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setShop(String shop) {
        mShop = shop;
    }

    public void setPrice(String price) {
        mPrice = price;
    }
    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}