package com.huiuoo.pc.common.constant;

import lombok.Getter;

@Getter
public enum EmRedisKey {

    USER_MESSAGE_CODE("com:huiuoo:user:smsCode","短信验证码");

    private String key;
    private String value;

    EmRedisKey(String key,String value){
        this.key = key;
        this.value = value;
    }

}
