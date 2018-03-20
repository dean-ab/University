
package bgu.spl181.net.json;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("bannedCountries")
    @Expose
    private List<String> bannedCountries = null;
    @SerializedName("availableAmount")
    @Expose
    private String availableAmount;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;
    private final static long serialVersionUID = -3779834686752390021L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Movie() {
    }

    /**
     * 
     * @param availableAmount
     * @param id
     * @param price
     * @param name
     * @param totalAmount
     * @param bannedCountries
     */
    public Movie(String id, String name, String price, List<String> bannedCountries, String availableAmount, String totalAmount) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.bannedCountries = bannedCountries;
        this.availableAmount = availableAmount;
        this.totalAmount = totalAmount;
    }

    public String toString() {
        return "// " + id + ", " + name + ", " + price + ", " + bannedCountries + "//";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getBannedCountries() {
        return bannedCountries;
    }

    public void setBannedCountries(List<String> bannedCountries) {
        this.bannedCountries = bannedCountries;
    }

    public String getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(String availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

}
