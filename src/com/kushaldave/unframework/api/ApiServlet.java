package com.kushaldave.unframework.api;

import com.kushaldave.unframework.api.base.ApiException;
import com.kushaldave.unframework.api.base.JsonResponse;
import com.kushaldave.unframework.gson.Jsonifier;
import com.kushaldave.unframework.models.Request;
import com.kushaldave.unframework.models.User;
import com.kushaldave.unframework.services.AppContext;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Handles and routes API requests */
public class ApiServlet extends HttpServlet {

  private final AppContext appContext;
  private final HelloEndpoints helloEndpoints;
  private final AuthEndpoints authEndpoints;

  public ApiServlet(AppContext appContext) {
    this.appContext = appContext;
    this.authEndpoints = new AuthEndpoints(appContext);
    this.helloEndpoints = new HelloEndpoints(appContext);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    handleRequest(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    handleRequest(req, resp);
  }

  /**
   * Route request to appropriate Endpoints subclass based on first path segment.
   *
   * For simplicity, coalesces GET and POST. Endpoints that wish to enforce one or the other can do so themselves.
   */
  private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    // Uncomment to enable CORS
    // resp.setHeader("Access-Control-Allow-Origin", "*");

    try {
      JsonResponse response = dispatch(req, resp);
      log(req, true);
      resp.getWriter().write(Jsonifier.toJson(response));
    } catch (ApiException e) {
      handleException(req, resp, e, e.responseCode);
    } catch (Exception e) {
      handleException(req, resp, e, 500);
    }
  }

  private JsonResponse dispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    String[] splitPath = req.getPathInfo().split("/");
    String pathStart = splitPath[1];
    if (pathStart.equals("auth")) {
      // Special case for auth, which doesn't have a user yet
      return authEndpoints.handle(splitPath, req, resp);
    } else {
      User user = appContext.mongoService.userFromToken(req.getParameter("oauth_token"));
      if (user == null) {
        throw new ApiException(403, "Invalid 'oauth_token'");
      }

      // This is the main dispatch.
      switch (pathStart) {
        case "hello":
          return helloEndpoints.handle(splitPath, req, user);
        default:
          throw new ApiException(404, "Unknown path '" + pathStart + "'");
      }

    }
  }

  /**
   * Generate a response from an Exception and also log it.
   * TODO: This is is placeholder. The exception should be sent to a logger, not stdout, and
   * the stack trace should not be sent to end users.
   */
  private void handleException(
      HttpServletRequest req,
      HttpServletResponse resp,
      Exception e,
      int responseCode
  ) throws IOException {
    e.printStackTrace();
    resp.setStatus(responseCode);
    resp.getWriter().write(Jsonifier.toJson(e));
    log(req, false);
  }

  /**
   * Log request and parameters to Mongo.
   * Easy enough to log additional information or change the destination.
   */
  private void log(HttpServletRequest req, boolean success) {
    Request r = new Request();
    r.id = new ObjectId();
    r.url = req.getRequestURI();
    r.ip = req.getHeader("X-Forwarded-For");
    r.userAgent = req.getHeader("User-Agent");
    r.params = Collections.list(req.getParameterNames()).stream().collect(Collectors.toMap(Function.identity(), req::getParameter));
    r.success = success;
    appContext.mongoService.executor.submit(() -> appContext.mongoService.saveRequest(r));
  }

}
