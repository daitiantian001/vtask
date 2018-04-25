package com.lmnml.group.controller.pdata;

import com.aliyun.oss.OSSClient;
import com.lmnml.group.common.model.R;
import com.lmnml.group.common.model.Result;
import com.lmnml.group.controller.BaseController;
import com.lmnml.group.util.StrKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by daitian on 2018/4/17.
 */
@RestController
@RequestMapping("pdata/img")
@Api(value = "平台文件接口", tags = {"平台文件接口"}, description = "plat文件传输")
public class PataImgController extends BaseController {

    @PostMapping("upload")
    @ApiOperation(value = "plat上传文件")
    public Result save(MultipartFile file, String ext) {
        try {
            String endpoint = "http://oss-cn-beijing.aliyuncs.com";
            String accessKeyId = "LTAIkaZXfjoFwrDw";
            String accessKeySecret = "Z4ngAnXvwiBnA9Xm6RrkukNsWjOlgi";
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            String id = StrKit.ID();
            String url = String.format("%s%s.%s", "http://yuejinimg.oss-cn-beijing.aliyuncs.com/", id, ext);
            InputStream inputStream = null;
            inputStream = file.getInputStream();
            ossClient.putObject("yuejinimg", id + "." + ext, inputStream);
            ossClient.shutdown();
            return new Result(R.SUCCESS, url);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result("文件上传失败!");
        }
    }

    @GetMapping("qrcode")
    @ApiOperation(value = "plat获取二维码")
    public void save(String content) {
//        return new Result(R.SUCCESS,"http://yuejinimg.oss-cn-beijing.aliyuncs.com/");
    }
}
