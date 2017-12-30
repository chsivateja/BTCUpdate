package btc.update.in;

import com.google.gson.annotations.SerializedName;

/**
 * Created by subhashhardaha on 03/12/17.
 */

public class Message {
    @SerializedName("body")
    private String body;
    @SerializedName("sender")
    private String sender;
    @SerializedName("timestamp")
    private String timestamp;

    public String getBody() {
        return body;
    }

    public Message(String body, String sender, String timestamp) {
        this.body = body;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
