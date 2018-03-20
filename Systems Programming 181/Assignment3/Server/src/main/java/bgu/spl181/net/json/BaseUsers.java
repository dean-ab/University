//
//package bgu.spl181.net;
//
//import java.io.Serializable;
//import java.util.List;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import org.apache.commons.lang.builder.ToStringBuilder;
//
//public class BaseUsers implements Serializable
//{
//
//    @SerializedName("users")
//    @Expose
//    private List<BaseUser> users = null;
//    private final static long serialVersionUID = 6308344190252877059L;
//
//    /**
//     * No args constructor for use in serialization
//     *
//     */
//    public BaseUsers() {
//    }
//
//    /**
//     *
//     * @param users
//     */
//    public BaseUsers(List<BaseUser> users) {
//        super();
//        this.users = users;
//    }
//
//    public List<BaseUser> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<BaseUser> users) {
//        this.users = users;
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this).append("users", users).toString();
//    }
//
//}
