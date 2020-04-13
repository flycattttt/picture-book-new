package com.huiuoo.pc.db.service.impl;

import com.huiuoo.pc.common.constant.SexType;
import com.huiuoo.pc.common.error.BusinessException;
import com.huiuoo.pc.common.error.EmBusinessError;
import com.huiuoo.pc.common.utils.CommonUtils;
import com.huiuoo.pc.common.utils.JuheApi;

import com.huiuoo.pc.db.dao.UserDao;
import com.huiuoo.pc.db.dao.UserOAuthDao;
import com.huiuoo.pc.db.dataobject.UserDO;
import com.huiuoo.pc.db.dataobject.UserOAuthDO;
import com.huiuoo.pc.db.service.IUserService;
import com.huiuoo.pc.db.vo.OAuthRegRequest;
import com.huiuoo.pc.db.vo.OAuthRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/17
 * @version：V1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserOAuthDao userOAuthDao;

    @Override
    public String sendSms(String phone) throws BusinessException {
        if (StringUtils.isBlank(phone)) {
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR, "手机号码不能为空");
        }
        String smsCode = CommonUtils.getSmsCode();
        JuheApi.sendValidationCode(phone, smsCode);
        return smsCode;
    }

    @Override
    public UserDO login(String phone) throws BusinessException, NoSuchAlgorithmException {
        if (StringUtils.isBlank(phone)){
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR);
        }
        UserDO userDO = userDao.findByPhone(phone);
        if (userDO == null) {
            // 注册用户
            userDO = new UserDO(
                    "灰灰"+CommonUtils.getRandom(1000),
                    phone,
                    SexType.OTHER.getType(),
                    ""
            );

        }else {
            userDO.setLastLoginTime(new Date());
            userDO.setUpdateTime(userDO.getLastLoginTime());
        }
        userDao.save(userDO);
        return userDO;
    }

/*    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserDO register(UserRegRequest request) throws BusinessException {
        UserDO oldUser = userDao.findByPhone(request.getPhone());
        if (oldUser != null) {
            throw new BusinessException(EmBusinessError.PHONE_IS_EXIST);
        }
        UserDO userDO = new UserDO();
        userDO.setName(request.getName());
        userDO.setPhone(request.getPhone());
        userDO.setIntegral(new BigDecimal(0));
        userDO.setHeadImg("");
        userDO.setSex(request.getSex()==null?SexType.OTHER.getType():request.getSex());
        userDao.save(userDO);
        return userDO;
    }*/

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserDO authLogin(OAuthRequest oAuthRequest) throws BusinessException {
        UserOAuthDO oldOauthUser = userOAuthDao.findByOauthTypeAndOpenId(oAuthRequest.getOauthType(), oAuthRequest.getOpenId());
        if (oldOauthUser == null) {
            throw new BusinessException(EmBusinessError.CAN_NOT_FIND_RECORD, "未找到该授权用户");
        }

        Optional<UserDO> oldUserDO = userDao.findById(oldOauthUser.getUserId());
        if (!oldUserDO.isPresent()) {
            throw new BusinessException(EmBusinessError.CAN_NOT_FIND_RECORD, "未找到授权对应的用户id");
        }

        // 刷新user_oauth
        oldOauthUser.setOauthAccessToken(oAuthRequest.getAccessToken());
        oldOauthUser.setOauthExpires(oAuthRequest.getExpires());
        oldOauthUser.setUpdateTime(new Date());
        userOAuthDao.save(oldOauthUser);

        // 刷新user
        UserDO userDO = oldUserDO.get();
        userDO.setLastLoginTime(new Date());
        userDO.setUpdateTime(userDO.getLastLoginTime());
        userDao.save(userDO);
        return userDO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserDO authRegister(OAuthRegRequest oAuthRegRequest) throws BusinessException {
        UserOAuthDO oldOauthUser = userOAuthDao.findByOauthTypeAndOpenId(oAuthRegRequest.getOauthType(), oAuthRegRequest.getOpenId());

        if (oldOauthUser!=null){
            throw new BusinessException(EmBusinessError.OAUTH_IS_EXIST);
        }

        // 获取user信息
        UserDO userDO = userDao.findByPhone(oAuthRegRequest.getPhone());
        if (userDO!=null){
            throw new BusinessException(EmBusinessError.PHONE_IS_EXIST);
        }

        // 添加用户
        userDO = new UserDO(
                oAuthRegRequest.getNickname(),
                oAuthRegRequest.getPhone(),
                SexType.OTHER.getType(),
                oAuthRegRequest.getHeadUrl()
        );

        userDao.save(userDO);

        // 添加用户授权
        oldOauthUser = new UserOAuthDO();
        oldOauthUser.setOauthType(oAuthRegRequest.getOauthType());
        oldOauthUser.setOpenId(oAuthRegRequest.getOpenId());
        oldOauthUser.setOauthAccessToken(oAuthRegRequest.getAccessToken());
        oldOauthUser.setOauthExpires(oAuthRegRequest.getExpires());
        oldOauthUser.setUpdateTime(new Date());
        // 获取新增的userId
        oldOauthUser.setUserId(userDO.getId());
        userOAuthDao.save(oldOauthUser);

        return userDO;
    }
}
