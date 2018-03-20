
package bgu.spl181.net.json;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BaseUser implements Serializable {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    private final static long serialVersionUID = 4880497467387332248L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BaseUser() {
    }

    /**
     * 
     * @param username
     * @param password
     */
    public BaseUser(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("username", username).append("password", password).toString();
    }

}
