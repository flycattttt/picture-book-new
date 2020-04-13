package com.huiuoo.pc.db.dao;

import com.alibaba.fastjson.JSON;
import com.huiuoo.pc.db.dataobject.ImageDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/4/10
 * @version：V1.0
 */
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class TagTest {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ImageTagDao tagDao;

    @Test
    public void test(){

    }

}
