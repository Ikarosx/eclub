package cn.eclubcc.common.exception;

import cn.eclubcc.common.exception.response.AuthCodeEnum;
import cn.eclubcc.common.exception.response.CommonCodeEnum;
import cn.eclubcc.common.exception.response.ResultCode;
import cn.eclubcc.pojo.http.response.ResponseResult;
import cn.eclubcc.pojo.http.response.ValidationErrorResponse;
import cn.eclubcc.pojo.http.response.ValidationMessage;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/28 16:03
 */
@ControllerAdvice
@Slf4j
public class ExceptionCatch {

  /** 使用EXCEPTIONS存放异常类型和错误代码的映射 */
  private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;

  /** 使用builder来构建ImmutableMap */
  private static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder =
      ImmutableMap.builder();

  static {
    // 在这里加入一些已经识别的异常类型判断
    builder.put(HttpMessageNotReadableException.class, CommonCodeEnum.INVALID_PARAM);
    builder.put(
        DataIntegrityViolationException.class, CommonCodeEnum.DATA_INTEGRITY_VIOLATION_EXCEPTION);
    builder.put(MethodArgumentNotValidException.class, CommonCodeEnum.INVALID_PARAM);
    builder.put(AccessDeniedException.class, AuthCodeEnum.ACCESS_DENIED_EXCEPTION);
    builder.put(
        HttpRequestMethodNotSupportedException.class,
        CommonCodeEnum.HTTP_REQUEST_METHOD_NOT_SUPPORT_EDEXCEPTION);
  }

  /**
   * 处理自定义异常
   *
   * @param e
   * @return
   */
  @ExceptionHandler(EClubException.class)
  @ResponseBody
  public ResponseResult customException(EClubException e) {
    log.error("catch exception : {}", e.getMessage());
    ResultCode resultCode = e.getResultCode();

    ResponseResult responseResult = new ResponseResult(resultCode);
    log.info(responseResult.toString());
    return responseResult;
  }

  @ExceptionHandler(BindException.class)
  @ResponseBody
  public ValidationErrorResponse bindException(BindException e) {
    log.error("catch exception : {}", e.getMessage());
    List<ObjectError> allErrors = e.getAllErrors();
    List<ValidationMessage> validationMessages = new ArrayList<>();
    for (ObjectError objectError : allErrors) {
      if (objectError instanceof FieldError) {
        FieldError fieldError = (FieldError) objectError;
        validationMessages.add(
            new ValidationMessage(fieldError.getField(), fieldError.getDefaultMessage()));
      } else {
        validationMessages.add(new ValidationMessage(null, objectError.getDefaultMessage()));
      }
    }
    ValidationErrorResponse responseResult =
        new ValidationErrorResponse(CommonCodeEnum.INVALID_PARAM, validationMessages);
    return responseResult;
  }

  /**
   * 处理其他未知异常
   *
   * @param exception
   * @return
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseResult exception(Exception exception) {
    // 记录日志
    log.error(
        "catch unknown exception:{}", exception.getClass() + "------" + exception.getMessage());
    if (EXCEPTIONS == null) {
      EXCEPTIONS = builder.build();
    }
    // 查看这个未知异常是否已经被我们记录下来
    ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
    ResponseResult responseResult;
    if (resultCode != null) {
      responseResult = new ResponseResult(resultCode);
    } else {
      // 未被记录下来则直接返回服务器内部错误
      responseResult = new ResponseResult(CommonCodeEnum.SERVER_ERROR);
    }
    return responseResult;
  }
}
