package fan.severclient;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/7/2 13:11.
 */

public class Header {

    private String serviceMethod;
    private int seq;
    private String errorMsg;

    public Header(String serviceMethod, int seq, String errorMsg) {
        this.serviceMethod = serviceMethod;
        this.seq = seq;
        this.errorMsg = errorMsg;
    }

    public Header() {
    }

    public String getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "Header{" +
                "serviceMethod='" + serviceMethod + '\'' +
                ", seq=" + seq +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}

