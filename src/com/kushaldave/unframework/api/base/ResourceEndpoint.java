package com.kushaldave.unframework.api.base;

import com.kushaldave.unframework.models.User;

import javax.servlet.http.HttpServletRequest;

/** An endpoint that takes a resource, e.g. /hello/48711/edit */
public interface ResourceEndpoint<T> {

  /**
   * Implementors should return an object they expect to be serialized to JSON
   * and throw an Exception for any failure cases.
   */
  JsonResponse run(HttpServletRequest req, User user, T resource);
}
