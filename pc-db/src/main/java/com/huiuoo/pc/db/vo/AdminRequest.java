package com.huiuoo.pc.db.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/4/1
 * @version：V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequest {

    private String name;
    private String password;

}
