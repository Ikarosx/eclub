package cn.eclubcc.controller;

import cn.eclubcc.pojo.filesystem.response.UploadFileResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ikaros
 * @date 2020/04/05 09:41
 */
@Api(value = "文件接口", tags = "文件接口")
public interface FileControllerApi {

  @ApiOperation(value = "上传文件", notes = "会返回url路径")
  UploadFileResult upload(MultipartFile file);

  @ApiOperation(value = "删除文件", notes = "传入参数形如group1/M00/00/00/L3C-116JRM-AaoIWAAK1MqNBP6g520.jpg")
  ResponseResult deleteFile(String id);
}
