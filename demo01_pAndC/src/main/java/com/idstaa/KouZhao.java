package com.idstaa;

/**
 * @author chenjie
 * @date 2021/2/18 21:43
 */
public class KouZhao {
    private String id;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public KouZhao(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
