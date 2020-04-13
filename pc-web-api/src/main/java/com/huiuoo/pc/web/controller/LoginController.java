package com.huiuoo.pc.web.controller;

import com.huiuoo.pc.common.advice.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/17
 * @version：V1.0
 */
@RequestMapping("login")
@Controller
public class LoginController extends BaseController {

     @GetMapping("index")
     public String index() {
         return "/index.html";
     }

}
