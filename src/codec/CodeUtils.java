package codec;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/25 18:02.
 */

public class CodeUtils {

    public static Codec getCodecByType(String encodingType) {
        if (encodingType.equals(EncodingTypeEnum.JsonType.getKey())) {
            return new CodecJsonImpl();
        } else  if (encodingType.equals(EncodingTypeEnum.GobType.getKey())){
            return new CodecGobImpl();
        }

        return null;
    }
}