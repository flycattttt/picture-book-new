package com.huiuoo.pc.db.service;


import com.huiuoo.pc.common.error.BusinessException;
import com.huiuoo.pc.db.dataobject.UserDO;
import com.huiuoo.pc.db.vo.OAuthRegRequest;
import com.huiuoo.pc.db.vo.OAuthRequest;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/17
 * @version：V1.0
 */
public interface IUserService {

    /** 发送 6 位数验证码 */
    String sendSms(String phone) throws BusinessException;

    /** 用户手机号码登录 */
    UserDO login(String phone) throws BusinessException, NoSuchAlgorithmException;

    /** 用户注册 */
   // UserDO register(UserRegRequest registerRequest) throws BusinessException;

    /** 用户第三方授权登录 */
    UserDO authLogin(OAuthRequest oAuthRequest) throws BusinessException;

    /** 用户第三方授权注册 */
    UserDO authRegister(OAuthRegRequest oAuthRegRequest) throws BusinessException;
}
