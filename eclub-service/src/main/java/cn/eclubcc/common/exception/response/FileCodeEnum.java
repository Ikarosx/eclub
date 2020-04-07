package cn.eclubcc.common.exception.response;

/**
 * @author Ikaros
 * @date 2020/3/30 15:56
 */
public enum FileCodeEnum implements ResultCode {
  /** 文件代码30200 */
  FILE_UPLOAD_FAIL(false, 30201, "文件上传失败"),
  FILE_DELETE_FAIL(false, 30201, "文件删除失败"),
  FILE_TOO_LARGE(false, 30299, "上传文件过大");

  FileCodeEnum(boolean success, int code, String message) {
    this.success = success;
    this.code = code;
    this.message = message;
  }

  private boolean success;
  private int code;
  private String message;

  @Override
  public boolean getSuccess() {
    return success;
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
