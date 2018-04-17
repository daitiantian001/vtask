package com.lmnml.group.controller.pdata;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by daitian on 2018/4/17.
 */
@RestController
@RequestMapping("pdata/img")
public class PataImgController extends BaseController{

    @RequestMapping("upload")
    public Result save(MultipartFile file,String ext){
        return new Result(R.SUCCESS,"http://yuejinimg.oss-cn-beijing.aliyuncs.com/");
    }

}
