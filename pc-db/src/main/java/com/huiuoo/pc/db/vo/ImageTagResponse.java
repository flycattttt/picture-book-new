package com.huiuoo.pc.db.vo;

import com.huiuoo.pc.db.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class ImageTagResponse {

    private Long id;
    private String url;
    private String followType;
    private String description;
    private List<ImageTagVO> tagVOList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageTagVO{
        private Long tagId;
        private String tag;
    }
}
