package com.huiuoo.pc.db.dao;


import com.huiuoo.pc.db.dataobject.UserOAuthDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @项目名称：picture-book-app-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/4/3
 * @version：V1.0
 */
@Repository
public interface UserOAuthDao extends JpaRepository<UserOAuthDO,Long> {

    UserOAuthDO findByOauthTypeAndOpenId(String oauthType, String openId);
}
