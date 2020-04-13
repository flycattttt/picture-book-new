package com.huiuoo.pc.db.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class ImageRequest {

    @NotNull(message = "图片不能为空")
    private MultipartFile file;

    @NotNull(message = "图片主类别不能为空")
    private Integer mainType;

    @NotBlank(message = "图片副类别不能为空")
    private String followType;

    @NotBlank(message = "图片描述不能为空")
    private String description;

    @NotNull(message = "图片宽度不能为空")
    private Integer width;

    @NotNull(message = "图片高度不能为空")
    private Integer height;

    private List<String> tagList;

}
