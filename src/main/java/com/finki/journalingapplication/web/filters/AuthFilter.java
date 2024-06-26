package com.finki.journalingapplication.web.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.finki.journalingapplication.model.User;

import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "ignore-path1", value = "/login"),
                @WebInitParam(name = "ignore-path2", value = "/register"),
                @WebInitParam(name = "ignore-path3", value = "/homepage"),
                @WebInitParam(name = "ignore-path4", value = "/auth-status"),
                @WebInitParam(name = "ignore-path5", value = "/video"),
                @WebInitParam(name = "ignore-path6", value = "/css"),
                @WebInitParam(name = "ignore-path7", value = "/js")})



public class AuthFilter implements Filter {
    private String[] ignorePaths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ignorePaths = new String[7];
        ignorePaths[0] = filterConfig.getInitParameter("ignore-path1");
        ignorePaths[1] = filterConfig.getInitParameter("ignore-path2");
        ignorePaths[2] = filterConfig.getInitParameter("ignore-path3");
        ignorePaths[3] = filterConfig.getInitParameter("ignore-path4");
        ignorePaths[4] = filterConfig.getInitParameter("ignore-path5");
        ignorePaths[5] = filterConfig.getInitParameter("ignore-path6");
        ignorePaths[6] = filterConfig.getInitParameter("ignore-path7");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        User user = (User) req.getSession().getAttribute("User");
        String uri = req.getRequestURI();


        if (user != null || uri.contains(ignorePaths[0]) || uri.contains(ignorePaths[1]) ||
                uri.contains(ignorePaths[2]) || uri.contains(ignorePaths[3]) ||
                uri.startsWith(ignorePaths[4]) || uri.startsWith(ignorePaths[5]) ||
                uri.startsWith(ignorePaths[6])

            //|| uri.startsWith("/contact") || uri.startsWith("/about") || uri.startsWith("/submitContactForm")
        ) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            resp.sendRedirect("/homepage");
        }

    }
}
