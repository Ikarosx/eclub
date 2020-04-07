package cn.eclubcc.controller.impl;

import cn.eclubcc.common.exception.ExceptionCast;
import cn.eclubcc.common.exception.response.FileCodeEnum;
import cn.eclubcc.controller.FileControllerApi;
import cn.eclubcc.pojo.filesystem.response.UploadFileResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 权限 TODO
 * @author Ikaros
 * @date 2020/04/05 09:41
 */
@RestController
@RequestMapping("file")
public class FileController implements FileControllerApi {
  @Autowired private FileSystemService fileSystemService;

  @Override
  @PostMapping
  public UploadFileResult upload(@NotNull MultipartFile file) {
    return fileSystemService.upload(file);
  }

  @Override
  @DeleteMapping
  public ResponseResult deleteFile(@NotEmpty String id) {
    return fileSystemService.deleteFile(id);
  }
}
