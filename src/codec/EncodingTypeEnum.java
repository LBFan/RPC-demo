package codec;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/25 17:26.
 */

public enum EncodingTypeEnum {

    GobType("gob", "application/gob"),
    JsonType("json", "application/json");
    /**
     * key
     */
    String key;

    /**
     * value
     */
    String value;

    EncodingTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}