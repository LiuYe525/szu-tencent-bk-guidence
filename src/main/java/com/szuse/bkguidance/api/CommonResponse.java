package com.szuse.bkguidance.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.Map;
import lombok.Data;

/**
 * @author LiuYe
 * @date 15/7/2023 下午11:38
 * @version 1.0
 */
@Data
public class CommonResponse {
  /** 操作是否成功 */
  private Boolean success;
  /** 响应编号 */
  private Integer code;
  /** 响应消息 */
  private String msg;
  /** 响应数据 */
  private Object data;
  /** 附加数据 */
  @JsonUnwrapped private Map<String, Object> appendData;

  public static CommonResponseBuilder builder() {
    return new CommonResponseBuilder();
  }
}
