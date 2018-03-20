
package bgu.spl181.net.json;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Users implements Serializable
{

    @SerializedName("users")
    @Expose
    private List<User> users = null;
    private final static long serialVersionUID = 7914193867821153959L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Users() {
    }

    /**
     * 
     * @param users
     */
    public Users(List<User> users) {
        super();
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("users", users).toString();
    }

    public void addUser(User us) {
        users.add(us);
    }
}
