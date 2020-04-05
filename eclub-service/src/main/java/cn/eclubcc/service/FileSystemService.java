package cn.eclubcc.service;

import cn.eclubcc.pojo.filesystem.response.UploadFileResult;
import cn.eclubcc.pojo.http.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ikaros
 * @date 2020/04/05 10:05
 */
public interface FileSystemService {
  UploadFileResult upload(MultipartFile file);

  ResponseResult deleteFile(String id);
}
