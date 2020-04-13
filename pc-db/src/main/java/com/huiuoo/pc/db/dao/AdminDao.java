package com.huiuoo.pc.db.dao;

import com.huiuoo.pc.db.dataobject.AdminDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/17
 * @version：V1.0
 */
@Repository
public interface AdminDao extends JpaRepository<AdminDO, Long> {

    AdminDO findByNameAndPassword(String name, String password);
}
