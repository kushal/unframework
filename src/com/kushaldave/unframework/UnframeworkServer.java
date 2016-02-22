package com.kushaldave.unframework;

import com.kushaldave.unframework.api.ApiServlet;
import com.kushaldave.unframework.services.AppContext;
import com.kushaldave.unframework.utils.HttpsOnlyFilter;
import com.kushaldave.unframework.web.WebServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import java.util.EnumSet;

/**
 * Run the unframework server!
 */
public class UnframeworkServer {
  public static void main(String[] args) throws Exception {
    Server server = new Server(Integer.valueOf(System.getenv("PORT")));

    ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
    servletContext.setContextPath("/");
    servletContext.setResourceBase(".");


    HandlerList handlers = new HandlerList();
    handlers.setHandlers(new Handler[] { servletContext });
    servletContext.getMimeTypes().addMimeMapping("js", "application/javascript; charset=UTF-8");
    server.setHandler(handlers);

    AppContext appContext = new AppContext();
    ServletHolder apiServletHolder = new ServletHolder(new ApiServlet(appContext));
    servletContext.setMaxFormContentSize(1000000);
    apiServletHolder.getRegistration().setMultipartConfig(
        new MultipartConfigElement(
            "/tmp",
            1024 * 1024,
            1024*1024,
            1024*1024*5));
    servletContext.addServlet(apiServletHolder, "/api/*");

    // Serve resources
    servletContext.addServlet(new ServletHolder(new DefaultServlet()), "/web/resources/*");
    servletContext.addServlet(new ServletHolder(new DefaultServlet()), "/favicon.ico");
    servletContext.addServlet(new ServletHolder(new WebServlet(appContext)), "/*");
    if (!"dev".equals(System.getenv("MODE"))) {
      servletContext.addFilter(HttpsOnlyFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    }
    server.start();
    server.join();
  }
}
