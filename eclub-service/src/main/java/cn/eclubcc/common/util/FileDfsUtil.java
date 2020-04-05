package cn.eclubcc.common.util;

import cn.eclubcc.common.exception.ExceptionCast;
import cn.eclubcc.common.exception.response.FileCodeEnum;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Ikaros
 * @date 2020/04/05 10:09
 */
@Component
public class FileDfsUtil {
  @Resource private FastFileStorageClient storageClient;

  /** 上传文件 */
  public String upload(MultipartFile multipartFile) throws Exception {
    String originalFilename =
        multipartFile
            .getOriginalFilename()
            .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
    StorePath storePath =
        this.storageClient.uploadImageAndCrtThumbImage(
            multipartFile.getInputStream(), multipartFile.getSize(), originalFilename, null);
    return storePath.getFullPath();
  }

  /** 删除文件 */
  public void deleteFile(String fileUrl) {

    try {
      StorePath storePath = StorePath.parseFromUrl(fileUrl);
      storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
    } catch (Exception e) {
      ExceptionCast.cast(FileCodeEnum.FILE_DELETE_FAIL);
    }
  }
}
