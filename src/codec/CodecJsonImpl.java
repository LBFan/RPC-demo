package codec;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/25 18:08.
 */

public class CodecJsonImpl implements Codec {


    @Override
    public void readHeader(Header header) throws Exception {

        System.out.println("this is json type");
    }

    @Override
    public void readBody(Object o) throws Exception {
        System.out.println("this is json type: " + o.toString());
    }

    @Override
    public void write(Header header, Object o) throws Exception {

    }
}

