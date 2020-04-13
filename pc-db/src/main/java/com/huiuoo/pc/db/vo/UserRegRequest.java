package com.huiuoo.pc.db.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
public class UserRegRequest {

    @NotBlank(message = "名称不能为空")
    private String name;
    private Integer sex;
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    @NotBlank(message = "验证码不能为空")
    private String smsCode;
}
