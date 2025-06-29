package com.fajarsn.taskmanager.shared.infrastructure.web;

import com.fajarsn.taskmanager.shared.infrastructure.JsonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Base servlet providing common functionality for REST endpoints.
 * Demonstrates servlet API usage and HTTP handling.
 */
public abstract class BaseServlet extends HttpServlet {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final JsonService jsonService;

    protected BaseServlet() {
        this.jsonService = new JsonService();
    }

    /**
     * Read JSON request body
     */
    protected String readRequestBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining("\n"));
    }

    /**
     * Write JSON response
     */
    protected void writeJsonResponse(HttpServletResponse response, Object data, int status) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);

        if (data != null) {
            String json = jsonService.toJson(data);
            response.getWriter().write(json);
        }
    }

    /**
     * Write error response
     */
    protected void writeErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        ErrorResponse error = new ErrorResponse(message, status);
        writeJsonResponse(response, error, status);
    }

    /**
     * Extract path parameter
     */
    protected String extractPathParameter(HttpServletRequest request, int index) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) return null;

        String[] parts = pathInfo.split("/");
        if (parts.length > index + 1) {
            return parts[index + 1];
        }

        return null;
    }

    /**
     * Check if request accepts JSON
     */
    protected boolean acceptsJson(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return accept != null && accept.contains("application/json");
    }

    /**
     * CORS headers
     */
    protected void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        addCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public record ErrorResponse(String message, int status) {}
}
