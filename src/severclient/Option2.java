package severclient;

import java.io.Serializable;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/29 23:37.
 */

public class Option2 implements Serializable {
    private String serverMethod2;
    private Long seq;
    private int magicNumber;
    private String codeType;

    public Option2(String serverMethod2, Long seq, int magicNumber, String codeType) {
        this.serverMethod2 = serverMethod2;
        this.seq = seq;
        this.magicNumber = magicNumber;
        this.codeType = codeType;
    }

    public String getServerMethod2() {
        return serverMethod2;
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

    public void setServerMethod2(String serverMethod2) {
        this.serverMethod2 = serverMethod2;
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
                "serverMethod='" + serverMethod2 + '\'' +
                ", seq=" + seq +
                ", magicNumber=" + magicNumber +
                ", codeType='" + codeType + '\'' +
                '}';
    }
}

