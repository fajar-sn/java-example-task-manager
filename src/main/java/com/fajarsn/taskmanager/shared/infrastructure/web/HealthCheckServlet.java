package com.fajarsn.taskmanager.shared.infrastructure.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "HealthCheckServlet", urlPatterns = {"/health"})
public class HealthCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        String healthStatus = String.format(
                "{\"status\":\"UP\", \"timestamp\":\"%s\", \"application\":\"Task Manager\"}",
                LocalDateTime.now().toString()
        );

        resp.getWriter().write(healthStatus);
    }
}
