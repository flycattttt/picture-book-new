package com.huiuoo.pc.db.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @项目名称：picture-book-app-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/4/8
 * @version：V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String name;
    private String phone;
    private BigDecimal integral;
    private Integer sex;
    private String headImg;
    private Date createTime;
    private Date lastLoginTime;
    private String token;
}
