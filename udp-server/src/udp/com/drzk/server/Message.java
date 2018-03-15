
package udp.com.drzk.server;

import java.io.Serializable;
import org.json.simple.JSONObject;

/**
 * 通讯消息体
 * @author LENOVO
 * 2018年3月14日
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private String sender;

    public String getSender() {
        return sender;
    }

    private String receiver;

    public String getReceiver() {
        return receiver;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setSender(String s) {
        sender = s;
    }

    public void setReceiver(String r) {
        receiver = r;
    }

    public void setMessage(String m) {
        message = m;
    }

    public Message(String s, String r, String m) {
        sender = s;
        receiver = r;
        message = m;
    }

    public Message(JSONObject o) {
        sender = (String) o.get("sender");
        receiver = (String) o.get("receiver");
        message = (String) o.get("message");
    }

    /**
     * Converts the Message into a JSONObject.
     * @return the JSONObject
     */
    @SuppressWarnings("unchecked")
	public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        o.put("sender", sender);
        o.put("receiver", receiver);
        o.put("message", message);
        return o;
    }

    @Override
    public String toString() {
        return "sender: " + sender + "; " +"receiver: " + receiver + "; " + "message:" + message;
    }

}
