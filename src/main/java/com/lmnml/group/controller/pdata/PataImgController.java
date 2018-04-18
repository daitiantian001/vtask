package com.lmnml.group.controller.pdata;

import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by daitian on 2018/4/17.
 */
@RestController
@RequestMapping("pdata/img")
@Api(value = "平台文件接口", tags = {"平台文件接口"}, description = "plat文件传输")
public class PataImgController extends BaseController{

    @RequestMapping("upload")
    @ApiOperation(value = "plat上传文件")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败")
    })
    public Result save(MultipartFile file,String ext){
        return new Result(R.SUCCESS,"http://yuejinimg.oss-cn-beijing.aliyuncs.com/");
    }

}
