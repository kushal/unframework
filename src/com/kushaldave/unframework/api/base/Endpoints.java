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

  private final Map<String, Endpoint> endpoints;
  private final Map<String, ResourceEndpoint<T>> resourceEndpoints;
  private final ResourceEndpoint<T> resourceDetailEndpoint;

  public Endpoints() {
    this.endpoints = getEndpoints();
    this.resourceEndpoints = getResourceEndpoints();
    this.resourceDetailEndpoint = getResourceDetailEndpoint();
  }

  public JsonResponse handle(String[] splitPath, HttpServletRequest req, User user) throws IOException, ApiException {

    if (splitPath.length < 3) {
      throw new ApiException(404, "No endpoint specified");
    }

    Endpoint endpoint = endpoints.get(splitPath[2]);
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
      ResourceEndpoint<T> resourceEndpoint = resourceEndpoints.get(splitPath[3]);
      if (resourceEndpoint != null) {
        return resourceEndpoint.run(req, user, resource);
      }
    }
    return resourceDetailEndpoint.run(req, user, resource);
  }

  /**
   * Given an ID, find the appropriate resource or return null.
   */
  protected abstract T findResource(String id);

  /**
   * Return a map from path to endpoints that aren't tied to a resource, e.g.
   * list for /hello/list
   * add for /hello/add
   * Called once and then cached.
   */
  protected abstract Map<String, Endpoint> getEndpoints();

  /**
   * Return a map from path to endpoints that are tied to a resource, e.g.
   * delete for /hello/48798/delete
   * edit for /hello/48798/edit
   * Called once and then cached.
   */
  protected abstract Map<String, ResourceEndpoint<T>> getResourceEndpoints();

  /**
   * Return the resource detail endpoint, i.e. what handles /hello/48798
   */
  protected abstract ResourceEndpoint<T> getResourceDetailEndpoint();
}