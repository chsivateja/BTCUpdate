package btc.update.in;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by subhashhardaha on 03/12/17.
 */

public class MessageResponse {
    @SerializedName("imei")
    private String imei;
    @SerializedName("messages")
    private List<Message> messages;
    @SerializedName("account")
    private List<String> account;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("uid")
    private String uid;

    public MessageResponse(String imei, List<Message> messages, List<String> account, String mobile,String uid) {
        this.imei = imei;
        this.messages = messages;
        this.account = account;
        this.mobile = mobile;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<String> getAccount() {
        return account;
    }

    public void setAccount(List<String> account) {
        this.account = account;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @Exclude
    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("imei", imei);
        result.put("messages", messages);
        result.put("account", account);
        result.put("mobile", mobile);
        result.put("uid",uid);

        return result;
    }
}
