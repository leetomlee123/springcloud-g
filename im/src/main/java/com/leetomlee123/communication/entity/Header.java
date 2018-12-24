package com.leetomlee123.communication.entity;

import java.io.Serializable;

public class Header implements Serializable {
    private int tag;
    private int commandCode;
    private int version;
    private int length;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(int commandCode) {
        this.commandCode = commandCode;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Header{" +
                "tag=" + tag +
                ", commandCode=" + commandCode +
                ", version=" + version +
                ", length=" + length +
                '}';
    }
}
