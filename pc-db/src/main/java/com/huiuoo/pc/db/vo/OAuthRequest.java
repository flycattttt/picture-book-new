package com.huiuoo.pc.db.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class OAuthRequest {
    @NotBlank(message = "oauthType不能为空")
    private String oauthType;
    @NotBlank(message = "openId不能为空")
    private String openId;
    @NotBlank(message = "accessToken不能为空")
    private String accessToken;
    @NotNull(message = "expires不能为空")
    private Integer expires;
}
