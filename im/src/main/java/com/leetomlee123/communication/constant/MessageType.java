package com.leetomlee123.communication.constant;

public enum MessageType {

    PLAIN("文本", 1), IMAGE("图片", 2), VIDEO("视频", 3),USERS("用户数",4),OTO("一对一",5),PING("PING",6);
    // 成员变量
    private String name;
    private Integer index;

    // 构造方法
    private MessageType(String name, Integer index) {
        this.name = name;
        this.index = index;
    }


    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
