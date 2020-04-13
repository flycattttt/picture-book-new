package com.huiuoo.pc.common.constant;

/**
 * @项目名称：picture-book-app-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/4/3
 * @version：V1.0
 */
public class LoginType {

    public static String by(Integer type) {
        switch (type) {
            case 1:
                return "手机号码登录";
            case 2:
                return "QQ登录";
            case 3:
                return "微信登录";
            default:
                return "其他登录";
        }
    }
}
