package com.huiuoo.pc.common.utils;

import com.huiuoo.pc.common.error.BusinessException;
import com.huiuoo.pc.common.error.EmBusinessError;
import org.apache.commons.lang3.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/18
 * @version：V1.0
 */
public class CommonUtils {

    public static String generateImageName(String fileName,String type) {

        StringBuilder buffer = new StringBuilder();
        buffer.append(type);
        //命名格式 bg_202003111739548_951.webp
        buffer.append(DateUtil.getMsTime());
        buffer.append("_");

        Random ran = new Random();
        int num = ran.nextInt(999);
        buffer.append(String.format("%03d", num));

        // 追加文件名后缀
        buffer.append(fileName.substring(fileName.lastIndexOf(".")));
        return buffer.toString();
    }
    /**
     * 描述：获取 0~num 之间的int随机数
     */
    public static int getRandom(int num) throws NoSuchAlgorithmException {
        if (num<0){
            return 0;
        }
        SecureRandom random= SecureRandom.getInstance("SHA1PRNG");
        return random.nextInt(num);
    }

    /**
     * 描述：生成短信验证码
     */
    public static String getSmsCode(){
        //按照一定的规则生成otp验证码
        Random random = new Random();
        int i = random.nextInt(999);
        i += 1000;
        return String.valueOf(i);
    }

    /**
     * 验证手机号格式
     */
    public static void validatorPhone(String phone) throws BusinessException {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR, "手机号应为11位数");
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR, "请填入正确的手机号");
            }
        }
    }
}
