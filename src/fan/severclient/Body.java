package fan.severclient;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/7/2 13:13.
 */

public class Body {

    private String payload;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Body{" +
                "payload='" + payload + '\'' +
                '}';
    }
}

