package com.huiuoo.pc.db.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
@Table(name = "pb_image_tag")
public class ImageTagDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Basic
    @Column(name = "image_id",nullable = false)
    private Long imageId;

    @Basic
    @Column(name = "img_tag",nullable = false)
    private String imgTag;


    public ImageTagDO(Long imageId,String imgTag){
        this.imageId = imageId;
        this.imgTag = imgTag;
    }
}
