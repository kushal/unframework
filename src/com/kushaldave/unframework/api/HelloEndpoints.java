package com.kushaldave.unframework.api;

import com.google.common.collect.ImmutableMap;
import com.kushaldave.unframework.api.base.Endpoint;
import com.kushaldave.unframework.api.base.Endpoints;
import com.kushaldave.unframework.api.base.JsonResponse;
import com.kushaldave.unframework.api.base.ResourceEndpoint;
import com.kushaldave.unframework.models.Hello;
import com.kushaldave.unframework.models.User;
import com.kushaldave.unframework.services.AppContext;
import org.bson.types.ObjectId;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Endpoints related to hellos.
 */
public class HelloEndpoints extends Endpoints<Hello> {

  private final AppContext appContext;

  public HelloEndpoints(AppContext appContext) {
    this.appContext = appContext;
  }

  @Override
  public Hello findResource(String id) {
    return appContext.mongoService.getHello(new ObjectId(id));
  }

  // Although we use functional interfaces for local methods here, as the code grows more complex we
  // can pull these into actual classes.
  private final Map<String,Endpoint> endpoints =
      new ImmutableMap.Builder<String, Endpoint>()
          .put("list", this::list)
          .build();

  @Override
  protected Map<String, Endpoint> getEndpoints() {
    return endpoints;
  }

  private final Map<String,ResourceEndpoint<Hello>> resourceEndpoints =
      new ImmutableMap.Builder<String, ResourceEndpoint<Hello>>()
          .put("edit", this::edit)
          .build();

  @Override
  protected Map<String, ResourceEndpoint<Hello>> getResourceEndpoints() {
    return resourceEndpoints;
  }

  @Override
  protected ResourceEndpoint<Hello> getResourceDetailEndpoint() {
    return this::detail;
  }

  private static class ListResponse implements JsonResponse {
    public List<Hello> hellos;
  }

  private JsonResponse list(HttpServletRequest request, User user) {
    ListResponse response = new ListResponse();
    response.hellos = appContext.mongoService.getHellos();
    return response;
  }

  private static class HelloResponse implements JsonResponse {
    public Hello hello;
  }

  private JsonResponse detail(HttpServletRequest request, User user, Hello hello) {
    HelloResponse response = new HelloResponse();
    response.hello = hello;
    return response;
  }

  private JsonResponse edit(HttpServletRequest request, User user, Hello hello) {
    // Don't actually do anything for now, but obviously use the params from request to mutate IRL.
    HelloResponse response = new HelloResponse();
    response.hello = hello;
    return response;
  }
}
