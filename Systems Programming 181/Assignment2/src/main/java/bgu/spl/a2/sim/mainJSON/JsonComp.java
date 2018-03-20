package bgu.spl.a2.sim.mainJSON;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonComp {

    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Sig Success")
    @Expose
    private String sigSuccess;
    @SerializedName("Sig Fail")
    @Expose
    private String sigFail;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSigSuccess() {
        return sigSuccess;
    }

    public void setSigSuccess(String sigSuccess) {
        this.sigSuccess = sigSuccess;
    }

    public String getSigFail() {
        return sigFail;
    }

    public void setSigFail(String sigFail) {
        this.sigFail = sigFail;
    }

}
