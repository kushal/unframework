package com.kushaldave.unframework.utils;

/**
 * Created by kushal on 11/9/15.
 */
public class ModeUtils {

  public static boolean isProd() {
    return !"dev".equals(System.getenv("MODE"));
  }
}
