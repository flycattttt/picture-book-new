package com.huiuoo.pc.db.service.impl;

import com.huiuoo.pc.common.constant.CommonStatus;
import com.huiuoo.pc.common.error.BusinessException;
import com.huiuoo.pc.common.error.EmBusinessError;
import com.huiuoo.pc.db.dao.AdminDao;
import com.huiuoo.pc.db.dataobject.AdminDO;
import com.huiuoo.pc.db.service.IAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/17
 * @version：V1.0
 */
@Service
public class AdminServiceImpl implements IAdminService {

    private final AdminDao adminDao;

    public AdminServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public AdminDO adminLogin(String name, String password) throws BusinessException {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)){
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR,"账号或密码不能为空");
        }
        AdminDO adminDO = adminDao.findByNameAndPassword(name, password);
        if (adminDO==null){
            throw new BusinessException(EmBusinessError.CAN_NOT_FIND_RECORD);
        }
        if (adminDO.getLockoutStatus().equals(CommonStatus.INVALID.getStatus())){
            throw new BusinessException(EmBusinessError.STATUS_IS_LOCKOUT);
        }
        return adminDO;
    }
}
