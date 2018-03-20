
package bgu.spl181.net.json;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class User implements Serializable
{

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("movies")
    @Expose
    private List<Movie> movies = null;
    @SerializedName("balance")
    @Expose
    private String balance;
    private final static long serialVersionUID = -4717402847489673189L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public User() {
    }

    /**
     * 
     * @param balance
     * @param username
     * @param movies
     * @param type
     * @param password
     * @param country
     */
    public User(String username, String type, String password, String country, List<Movie> movies, String balance) {
        super();
        this.username = username;
        this.type = type;
        this.password = password;
        this.country = country;
        this.movies = movies;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "//" + username + ", " + type + ", " + password + ", " + country +"//";

    }

}
