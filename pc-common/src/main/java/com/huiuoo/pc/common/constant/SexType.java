package com.huiuoo.pc.common.constant;

import lombok.Getter;

@Getter
public enum SexType {

    OTHER(0, "未指定"),
    WOMAN(1, "女性"),
    MAN(2, "男性");

    private Integer type;
    private String desc;

    SexType(Integer status, String desc) {
        this.type = status;
        this.desc = desc;
    }
}
