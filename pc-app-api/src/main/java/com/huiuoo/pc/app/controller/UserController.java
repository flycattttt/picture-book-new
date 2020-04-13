package com.huiuoo.pc.app.controller;

import com.huiuoo.pc.app.common.jwt.JwtToken;
import com.huiuoo.pc.common.advice.BaseController;
import com.huiuoo.pc.common.constant.EmRedisKey;
import com.huiuoo.pc.common.error.BusinessException;
import com.huiuoo.pc.common.error.EmBusinessError;
import com.huiuoo.pc.common.response.CommonReturnType;
import com.huiuoo.pc.common.utils.CommonUtils;
import com.huiuoo.pc.common.utils.RedisUtil;
import com.huiuoo.pc.common.validator.ValidationResult;
import com.huiuoo.pc.common.validator.ValidatorImpl;
import com.huiuoo.pc.db.dataobject.UserDO;
import com.huiuoo.pc.db.service.IUserService;
import com.huiuoo.pc.db.vo.OAuthRegRequest;
import com.huiuoo.pc.db.vo.OAuthRequest;
import com.huiuoo.pc.db.vo.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/17
 * @version：V1.0
 */
@Slf4j
@RequestMapping("user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("sendSms")
    public CommonReturnType senSms(@RequestParam("phone") String phone) throws BusinessException {

        // 手机号码验证
        CommonUtils.validatorPhone(phone);

        Object temporary = redisUtil.hget(EmRedisKey.USER_MESSAGE_CODE.getKey()+"_temporary",phone);
        if (temporary!=null){
            throw new BusinessException(EmBusinessError.SEND_MESSAGE_CODE_FORBID);
        }

        String smsCode = userService.sendSms(phone);
        redisUtil.hset(EmRedisKey.USER_MESSAGE_CODE.getKey()+"_temporary",phone,1,60);
        redisUtil.hset(EmRedisKey.USER_MESSAGE_CODE.getKey(),phone,smsCode,1800);
        return CommonReturnType.create(null);
    }

    @PostMapping("login")
    public CommonReturnType login(String phone, String smsCode) throws BusinessException, NoSuchAlgorithmException {

        CommonUtils.validatorPhone(phone);
        if (StringUtils.isBlank(smsCode)) {
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR, "验证码不能为空");
        }
        //比对短信验证码
        String code = (String) redisUtil.hget(EmRedisKey.USER_MESSAGE_CODE.getKey(), phone);
        if (!StringUtils.equals(code, smsCode)) {
            throw new BusinessException(EmBusinessError.SMSCODE_IS_ERROR);
        }
        UserDO userDO = userService.login(phone);
        return CommonReturnType.create(covert2UserResponse(userDO, JwtToken.createToken(userDO.getId())));
    }

    /*    @PostMapping("register")
        public CommonReturnType register(UserRegRequest registerRequest) throws BusinessException {
            ValidationResult validate = validator.validate(registerRequest);
            if(validate.isHasErrors()){
                throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR,validate.getErrMsg());
            }

            //将sms验证码与用户的手机号关联，使用httpSession绑定
            String code = (String)request.getSession().getAttribute(registerRequest.getPhone());
            if (!StringUtils.equals(code,registerRequest.getSmsCode())){
                throw new BusinessException(EmBusinessError.SMSCODE_IS_ERROR);
            }
            UserDO userDO = userService.register(registerRequest);
            String token = JwtToken.createToken(userDO.getId());
            return CommonReturnType.create(token);
        }*/
    @PostMapping("authLogin")
    public CommonReturnType authLogin(OAuthRequest oAuthRequest) throws BusinessException {
        ValidationResult validate = validator.validate(oAuthRequest);
        if (validate.isHasErrors()) {
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR, validate.getErrMsg());
        }
        UserDO userDO = userService.authLogin(oAuthRequest);
        return CommonReturnType.create(covert2UserResponse(userDO,JwtToken.createToken(userDO.getId())));
    }

    @PostMapping("authRegister")
    public CommonReturnType authRegister(OAuthRegRequest regRequest) throws BusinessException {

        // 手机号码格式验证
        CommonUtils.validatorPhone(regRequest.getPhone());

        //比对短信验证码
        String code = (String) redisUtil.hget(EmRedisKey.USER_MESSAGE_CODE.getKey(), regRequest.getPhone());
        if (!StringUtils.equals(code, regRequest.getSmsCode())) {
            throw new BusinessException(EmBusinessError.SMSCODE_IS_ERROR);
        }
        ValidationResult validate = validator.validate(regRequest);
        if (validate.isHasErrors()) {
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR, validate.getErrMsg());
        }
        UserDO userDO = userService.authRegister(regRequest);

        return CommonReturnType.create(covert2UserResponse(userDO,JwtToken.createToken(userDO.getId())));
    }


    private UserResponse covert2UserResponse(UserDO userDO, String token){
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(userDO,response);
        response.setToken(token);
        return response;
    }
}
