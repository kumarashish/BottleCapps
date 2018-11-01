package model;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 07-06-2018.
 */

public class ProductDetails {
    int ProductId;
    String UPC;
    String SKU;
    String ProductName;
    String Price;
    String StrikedPrice;
    boolean ClubPriceFlag;
    int Quantity;
    String productImage;
    public ProductDetails(JSONObject jsonObject)
    {try {
        this.ProductId = jsonObject.isNull("ProductId") ? 0 : jsonObject.getInt("ProductId");
        this.UPC= jsonObject.isNull("UPC") ? "" : jsonObject.getString("UPC");
        this.SKU= jsonObject.isNull("SKU") ? "" : jsonObject.getString("SKU");
        this.ProductName= jsonObject.isNull("ProductName") ? "" : jsonObject.getString("ProductName");
        this.Price= jsonObject.isNull("Price") ? "" : jsonObject.getString("Price");
        this.StrikedPrice= jsonObject.isNull("StrikedPrice") ? "" : jsonObject.getString("StrikedPrice");
        this.ClubPriceFlag= jsonObject.isNull("ClubPriceFlag") ? false : jsonObject.getBoolean("ClubPriceFlag");
        this.Quantity= jsonObject.isNull("Quantity") ? 0 : jsonObject.getInt("Quantity");
        this.productImage= jsonObject.isNull("ImageUrl") ? "" : jsonObject.getString("ImageUrl");
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
    }

    public int getProductId() {
        return ProductId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getPrice() {
        return Price;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getSKU() {
        return SKU;
    }

    public String getStrikedPrice() {
        return StrikedPrice;
    }

    public String getUPC() {
        return UPC;
    }

    public String getProductImage() {
        return productImage;
    }

    public boolean isClubPriceFlag() {
        return ClubPriceFlag;
    }
}
