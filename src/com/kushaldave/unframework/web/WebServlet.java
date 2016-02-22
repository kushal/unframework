package com.kushaldave.unframework.web;

import com.kushaldave.unframework.services.AppContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Handles and routes API requests */
public class WebServlet extends HttpServlet {

    private final AppContext appContext;

    public WebServlet(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String scriptLocation = "http://localhost:9090/assets/app.js";
        if (!"dev".equals(System.getenv("MODE"))) {
            scriptLocation = "/web/resources/app.js";
        }
        resp.getWriter().write("" +
                "<html><head>" +
                "<title>Unframework</title>" +
                "</head><body>" +
                "<div id=contents></div>" +
                "<script src=\"" + scriptLocation + "\" text=\"text/javascript\"></script>" +
                "</body></html>");
    }
}
