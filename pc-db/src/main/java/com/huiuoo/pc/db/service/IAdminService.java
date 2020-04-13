package com.huiuoo.pc.db.service;

import com.huiuoo.pc.common.error.BusinessException;
import com.huiuoo.pc.db.dataobject.AdminDO;
import org.springframework.stereotype.Component;


/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/17
 * @version：V1.0
 */
public interface IAdminService {

    /**
     * 描述：管理员登录
     */
    AdminDO adminLogin(String name, String password) throws BusinessException;
}
