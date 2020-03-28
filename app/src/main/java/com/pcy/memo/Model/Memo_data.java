package com.pcy.memo.Model;

import java.io.Serializable;

public class Memo_data implements Serializable {
    private String memo;
    private int color;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
