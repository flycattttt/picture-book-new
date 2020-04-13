package com.huiuoo.pc.db.dao;


import com.huiuoo.pc.db.dataobject.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @项目名称：picture-book-app-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/4/2
 * @version：V1.0
 */
@Repository
public interface UserDao extends JpaRepository<UserDO,Long> {

    UserDO findByPhone(String phone);
}
