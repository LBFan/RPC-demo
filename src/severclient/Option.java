package severclient;

import java.io.Serializable;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/29 23:37.
 */

public class Option implements Serializable {
    private String serverMethod;
    private Long seq;
    private int magicNumber;
    private String codeType;

    public Option(String serverMethod, Long seq, int magicNumber, String codeType) {
        this.serverMethod = serverMethod;
        this.seq = seq;
        this.magicNumber = magicNumber;
        this.codeType = codeType;
    }

    public String getServerMethod() {
        return serverMethod;
    }

    public Long getSeq() {
        return seq;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setServerMethod(String serverMethod) {
        this.serverMethod = serverMethod;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    @Override
    public String toString() {
        return "Option{" +
                "serverMethod='" + serverMethod + '\'' +
                ", seq=" + seq +
                ", magicNumber=" + magicNumber +
                ", codeType='" + codeType + '\'' +
                '}';
    }
}

