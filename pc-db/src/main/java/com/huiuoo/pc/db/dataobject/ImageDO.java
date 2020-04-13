package com.huiuoo.pc.db.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
@Table(name = "pb_image")
public class ImageDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Basic
    @Column(name = "url",nullable = false)
    private String url;

    @Basic
    @Column(name = "main_type",nullable = false)
    private Integer mainType;

    @Basic
    @Column(name = "follow_type",nullable = false)
    private String followType;
    @Basic
    @Column(name = "description",nullable = false)
    private String description;
    @Basic
    @Column(name = "size",nullable = false)
    private Long size;

    @Basic
    @Column(name = "width",nullable = false)
    private Integer width;

    @Basic
    @Column(name = "height",nullable = false)
    private Integer height;

    @Basic
    @Column(name = "admin_id",nullable = false)
    private Long adminId;

    @Basic
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time",nullable = false)
    private Date updateTime;

    public ImageDO(String url,Integer mainType,String followType,String description,
                   Long size, Integer width, Integer height,Long adminId) {
        this.url = url;
        this.mainType = mainType;
        this.followType = followType;
        this.description = description;
        this.size = size;
        this.width = width;
        this.height = height;
        this.adminId = adminId;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="image_id")
    private List<ImageTagDO> imageTagDOS;
}
