package com.huiuoo.pc.db.dao;


import com.huiuoo.pc.db.dataobject.ImageTagDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/17
 * @version：V1.0
 */
@Repository
public interface ImageTagDao extends JpaRepository<ImageTagDO,Long> {

    @Query("select it.imageId FROM ImageTagDO it where it.imgTag = ?1")
    List<Long> findImageIdByTag(String imgTag);
}
