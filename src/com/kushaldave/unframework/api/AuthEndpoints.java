package com.kushaldave.unframework.api;

import com.kushaldave.unframework.api.base.ApiException;
import com.kushaldave.unframework.api.base.JsonResponse;
import com.kushaldave.unframework.models.User;
import com.kushaldave.unframework.services.AppContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Endpoints related to authentication.
 */
public class AuthEndpoints {
  private final AppContext appContext;

  public AuthEndpoints(AppContext appContext) {
    this.appContext = appContext;
  }

  public JsonResponse handle(String[] splitPath, HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ApiException {
    switch (splitPath[2]) {
      case "gettoken":
        return getToken(req, resp);
    }
    throw new ApiException(404, "Endpoint " + splitPath[2] + " not found");
  }

  private static class TokenResponse implements JsonResponse {
    public User user;
    public String token;
  }

  private JsonResponse getToken(HttpServletRequest req, HttpServletResponse resp) {
    // Placeholder. Obviously get the user based on a password or other credential.
    User user = appContext.mongoService.getUser();
    TokenResponse response = new TokenResponse();
    response.token = user.token;
    response.user = user;
    return response;
  }
}
