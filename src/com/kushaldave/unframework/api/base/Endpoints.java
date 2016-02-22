package com.kushaldave.unframework.api.base;

import com.kushaldave.unframework.models.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * A collection of endpoints, for example /hello/*
 *
 * This is of course just one possible approach. It's possible to adapt this interface to be higher-level
 * (custom request object) or lower level (pass HttpServletResponse down).
 */
public abstract class Endpoints<T> {

  public JsonResponse handle(String[] splitPath, HttpServletRequest req, User user) throws IOException, ApiException {

    Endpoint endpoint = getEndpoints().get(splitPath[2]);
    if (endpoint != null) {
      return endpoint.run(req, user);
    }

    T resource = findResource(splitPath[2]);
    if (resource == null) {
      throw new ApiException(404, "Resource not found");
    }
    if (splitPath.length > 3) {
      throw new ApiException(404, "Too many path segments");
    }
    if (splitPath.length == 3) {
      ResourceEndpoint<T> resourceEndpoint = getResourceEndpoints().get(splitPath[3]);
      if (resourceEndpoint != null) {
        return resourceEndpoint.run(req, user, resource);
      }
    }
    return getResourceDetailEndpoint().run(req, user, resource);
  }

  /**
   * Given an ID, find the appropriate resource or return null.
   */
  protected abstract T findResource(String id);

  /**
   * Return a map from path to endpoints that aren't tied to a resource, e.g.
   * list for /hello/list
   * add for /hello/add
   */
  protected abstract Map<String, Endpoint> getEndpoints();

  /**
   * Return a map from path to endpoints that are tied to a resource, e.g.
   * delete for /hello/48798/delete
   * edit for /hello/48798/edit
   */
  protected abstract Map<String, ResourceEndpoint<T>> getResourceEndpoints();

  /**
   * Return the resource detail endpoint, i.e. what handles /hello/48798
   */
  protected abstract ResourceEndpoint<T> getResourceDetailEndpoint();
}