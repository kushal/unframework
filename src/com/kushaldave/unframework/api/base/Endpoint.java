package com.kushaldave.unframework.api.base;

import com.kushaldave.unframework.models.User;

import javax.servlet.http.HttpServletRequest;

/** An endpoint that doesn't have a specific resource, e.g. /hello/list */
public interface Endpoint {

  /**
   * Implementors should return an object they expect to be serialized to JSON
   * and throw an Exception for any failure cases.
   */
  JsonResponse run(HttpServletRequest req, User user);
}
