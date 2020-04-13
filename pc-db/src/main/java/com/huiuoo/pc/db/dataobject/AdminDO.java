package com.huiuoo.pc.db.dataobject;

import com.huiuoo.pc.common.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/17
 * @version：V1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pb_admin")
public class AdminDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "lockout_status", nullable = false)
    private Integer lockoutStatus;

    @Basic
    @Column(name = "phone", nullable = false)
    private String phone;

    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdminDO(String name,
                   String password, Integer lockoutStatus, String phone) {
        this.name = name;
        this.password = password;
        this.lockoutStatus = CommonStatus.VALID.getStatus();;
        this.phone = phone;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }
}
