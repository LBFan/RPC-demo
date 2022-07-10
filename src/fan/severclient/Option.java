package fan.severclient;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/7/2 12:56.
 */

public class Option {

    private int magicNumber;
    private String codeType;

    public Option(int magicNumber, String codeType) {
        this.magicNumber = magicNumber;
        this.codeType = codeType;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    @Override
    public String toString() {
        return "Option{" +
                "magicNumber=" + magicNumber +
                ", codeType='" + codeType + '\'' +
                '}';
    }
}

