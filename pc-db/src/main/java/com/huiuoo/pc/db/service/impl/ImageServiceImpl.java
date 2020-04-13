package com.huiuoo.pc.db.service.impl;

import com.huiuoo.pc.common.constant.ImageType;
import com.huiuoo.pc.common.error.BusinessException;
import com.huiuoo.pc.common.error.EmBusinessError;
import com.huiuoo.pc.common.utils.CommonUtils;
import com.huiuoo.pc.common.utils.QiniuUtils;
import com.huiuoo.pc.db.dao.ImageTagDao;
import com.huiuoo.pc.db.dataobject.ImageDO;
import com.huiuoo.pc.db.dataobject.ImageTagDO;
import com.huiuoo.pc.db.dao.ImageDao;
import com.huiuoo.pc.db.dto.ImageDTO;
import com.huiuoo.pc.db.service.IImageService;
import com.huiuoo.pc.db.vo.ImageRequest;
import com.huiuoo.pc.db.vo.ImageTagResponse;
import com.qiniu.common.QiniuException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @项目名称：picture-book-app-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/4/2
 * @version：V1.0
 */
@Service
public class ImageServiceImpl implements IImageService {

    @Resource
    private ImageDao imageDao;

    @Resource
    private ImageTagDao imageTagDao;

    @Override
    public List<ImageTagResponse> findByMainType(Integer mainType) throws BusinessException {
        if (mainType==null || mainType==0){
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR, "一级类别不能为空");
        }
        // 根据一级类别查询
        List<ImageDO> imageDOList = imageDao.findByMainType(mainType);
        if (CollectionUtils.isEmpty(imageDOList)){
            return null;
        }
        List<ImageTagResponse> responseList = new ArrayList<>();
        imageDOList.forEach(i->{
            ImageTagResponse response = new ImageTagResponse();
            response.setId(i.getId());
            response.setUrl(i.getUrl());
            response.setFollowType(i.getFollowType());
            response.setDescription(i.getDescription());
            List<ImageTagResponse.ImageTagVO> imageTagVOList = new ArrayList<>();
            // imgTag
            i.getImageTagDOS().forEach(t->{
                ImageTagResponse.ImageTagVO imageTagVO = new ImageTagResponse.ImageTagVO();
                imageTagVO.setTagId(t.getId());
                imageTagVO.setTag(t.getImgTag());
                imageTagVOList.add(imageTagVO);
            });

            response.setTagVOList(imageTagVOList);
            responseList.add(response);
        });
        return responseList;
    }
    @Override
    public List<ImageDO> findByMainTypeAndTag(Integer mainType, String imgTag) throws BusinessException {
        if (mainType==null || mainType==0){
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR, "一级类别不能为空");
        }
        if (StringUtils.isBlank(imgTag)){
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR, "标签不能为空");
        }

        List<Long> tag = imageTagDao.findImageIdByTag(imgTag);
        return imageDao.findByMainTypeAndIdIn(mainType, tag);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ImageDO insertImage(ImageRequest request, Long userId) throws QiniuException, BusinessException {

        String imageName = CommonUtils.generateImageName(
                Objects.requireNonNull(request.getFile().getOriginalFilename()), ImageType.to(request.getMainType()));

        ImageDO imageDO = new ImageDO(
                imageName,
                request.getMainType(),
                request.getFollowType(),
                request.getDescription(),
                request.getFile().getSize(),
                request.getWidth(),
                request.getHeight(),
                userId
        );
        ImageDO saveImage = imageDao.save(imageDO);
        if (CollectionUtils.isNotEmpty(request.getTagList())) {
            // 去除重复标签
            List<String> uniqueTag = new ArrayList<>(new HashSet<>(request.getTagList()));
            List<ImageTagDO> imageTagDOS = new ArrayList<>();
            uniqueTag.forEach(tag -> {
                ImageTagDO imageTagDO = new ImageTagDO(saveImage.getId(), tag);
                imageTagDOS.add(imageTagDO);
            });
            imageTagDao.saveAll(imageTagDOS);
        }
        QiniuUtils.uploadImage(request.getFile(), imageName);
        return saveImage;
    }
}
