package com.masolria.servlet.Filter;

import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import jakarta.servlet.*;

@WebFilter("/*")
public class EncodingMimeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
       req.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        chain.doFilter(req,res);
    }
}