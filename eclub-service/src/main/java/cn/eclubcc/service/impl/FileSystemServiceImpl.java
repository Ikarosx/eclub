package cn.eclubcc.service.impl;

import cn.eclubcc.common.exception.ExceptionCast;
import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.common.exception.response.FileCodeEnum;
import cn.eclubcc.common.util.FileDfsUtil;
import cn.eclubcc.pojo.filesystem.response.UploadFileResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.service.FileSystemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Ikaros
 * @date 2020/2/20 14:20
 */
@Service("FileSystemService")
public class FileSystemServiceImpl implements FileSystemService {
  @Resource private FileDfsUtil fileDfsUtil;

  @Value("${eclub.imgDomain}")
  private String imgDomain;

  @Override
  public UploadFileResult upload(
      MultipartFile file) {

    // 上传文件到fdfs
    String fileId = null;
    try {
      fileId = fileDfsUtil.upload(file);
    } catch (Exception e) {
      e.printStackTrace();
      ExceptionCast.cast(FileCodeEnum.FILE_UPLOAD_FAIL);
    }
    return new UploadFileResult(CommonCodeEnum.SUCCESS, imgDomain + fileId);
  }

  @Override
  public ResponseResult deleteFile(String id) {
    fileDfsUtil.deleteFile(id);
    return new ResponseResult(CommonCodeEnum.SUCCESS);
  }
}
