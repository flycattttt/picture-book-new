package com.huiuoo.pc.db.service;

import com.huiuoo.pc.common.error.BusinessException;
import com.huiuoo.pc.db.dataobject.ImageDO;
import com.huiuoo.pc.db.vo.ImageRequest;
import com.huiuoo.pc.db.vo.ImageTagResponse;
import com.qiniu.common.QiniuException;

import java.util.List;

/**
 * @项目名称：picture-book-app-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/4/2
 * @version：V1.0
 */

public interface IImageService {


    /** 描述： 根据图片一级类别查找  */
    List<ImageTagResponse> findByMainType(Integer mainType) throws BusinessException;

    /** 描述： 根据图片一级类别和标签查找  */
    List<ImageDO> findByMainTypeAndTag(Integer mainType, String imgTag) throws BusinessException;

    ImageDO insertImage(ImageRequest request, Long userId) throws QiniuException, BusinessException;


}
