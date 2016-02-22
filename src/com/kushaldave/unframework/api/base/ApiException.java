package com.kushaldave.unframework.api.base;

/**
 * Created by kushal on 6/25/15.
 */
public class ApiException extends Exception {
  public final int responseCode;
  public final String detail;

  public ApiException(int responseCode, String detail, Exception exception) {
    super(exception);
    this.responseCode = responseCode;
    this.detail = detail;
  }

  public ApiException(int responseCode, String detail) {
    this(responseCode, detail, null);
  }
}
