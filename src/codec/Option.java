package codec;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/29 22:53.
 */

public class Option {
    private Codec codec;
    private int magicNumber;

    public Option(Codec codec, int magicNumber) {
        this.codec = codec;
        this.magicNumber = magicNumber;
    }
}

