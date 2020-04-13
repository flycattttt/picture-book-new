package com.huiuoo.pc.db.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @项目名称：picture-book-app-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/4/3
 * @version：V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user_oauth")
public class UserOAuthDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "user_id")
    private Long userId;
    @Basic
    @Column(name = "oauth_type")
    private String oauthType;
    @Basic
    @Column(name = "open_id")
    private String openId;
    @Basic
    @Column(name = "oauth_access_token")
    private String oauthAccessToken;
    @Basic
    @Column(name = "oauth_expires")
    private Integer oauthExpires;
    @Basic
    @Column(name = "update_time")
    private Date updateTime;
}
