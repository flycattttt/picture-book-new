package com.huiuoo.pc.app.controller;


import com.huiuoo.pc.common.advice.BaseController;
import com.huiuoo.pc.common.error.BusinessException;
import com.huiuoo.pc.common.response.CommonReturnType;
import com.huiuoo.pc.db.dataobject.ImageDO;
import com.huiuoo.pc.db.service.IImageService;
import com.huiuoo.pc.db.vo.ImageTagResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/3/17
 * @version：V1.0
 */
@RequestMapping("image")
@RestController
public class ImageController extends BaseController {

    private final IImageService imageService;

    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("get/mainType/{mainType}")
    public CommonReturnType findImage(@PathVariable("mainType") Integer mainType) throws BusinessException {

        List<ImageTagResponse> responses = imageService.findByMainType(mainType);
        return CommonReturnType.create(responses);
    }

    @GetMapping("get/mainType/{mainType}/imgTag")
    public CommonReturnType findImage(@PathVariable("mainType") Integer mainType,
                                      @RequestParam("imgTag") String imgTag) throws BusinessException {

        List<ImageDO> typeAndTag = imageService.findByMainTypeAndTag(mainType, imgTag);
        return CommonReturnType.create(typeAndTag);
    }

   /* @GetMapping("get")
    public CommonReturnType findByEs(@RequestParam String des) {

        Map<String,Object> map = new HashMap<>();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.should(QueryBuilders.matchPhraseQuery("description",des));
        builder.should(QueryBuilders.matchPhraseQuery("follow_type",des));
        String string = builder.toString();
        System.out.println(string);

        Page<ImageES> search = (Page<ImageES>) imageESDao.search(builder);
        List<ImageES> content = search.getContent();
        stopWatch.stop();
        long millis = stopWatch.getTotalTimeMillis();
        map.put("time",millis);
        map.put("data",content);
        return CommonReturnType.create(map);
    }*/

}
