package com.huiuoo.pc.common.constant;

public class ImageType {

    public static String to(Integer eventType) {
        switch (eventType) {
            case 1:
                return "bg_";
            case 2:
                return "element_";
            case 3:
                return "model_";
            default:
                return "other_";
        }
    }
}
