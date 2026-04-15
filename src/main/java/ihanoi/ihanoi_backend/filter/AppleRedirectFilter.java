package ihanoi.ihanoi_backend.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AppleRedirectFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        if (uri.endsWith("apple-app-site-association")) {
            request.getRequestDispatcher("/api/v1/file/download/apple-app-site-association")
                    .forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
