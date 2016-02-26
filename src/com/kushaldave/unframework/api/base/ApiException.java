package com.kushaldave.unframework.api.base;

/**
 * Created by kushal on 6/25/15.
 */
public class ApiException extends Exception {
  public final int responseCode;

  public ApiException(int responseCode, String detail, Exception exception) {
    super(detail, exception);
    this.responseCode = responseCode;
  }

  public ApiException(int responseCode, String detail) {
    this(responseCode, detail, null);
  }

}
