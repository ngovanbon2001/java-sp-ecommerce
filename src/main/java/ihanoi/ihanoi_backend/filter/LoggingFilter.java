package ihanoi.ihanoi_backend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ihanoi.ihanoi_backend.configuration.security.RequestScope;
import ihanoi.ihanoi_backend.util.BeanUtils;
import ihanoi.ihanoi_backend.util.DateTimeUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.net.InetAddress;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoggingFilter extends OncePerRequestFilter {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        ContentCachingResponseWrapper cacheResponse = new ContentCachingResponseWrapper(response);
        ContentCachingRequestWrapper reqWrapper = new ContentCachingRequestWrapper(request);
        try {
            filterChain.doFilter(reqWrapper, cacheResponse);
        } finally {
            sendlogs(request, cacheResponse, startTime, reqWrapper);
        }
    }

    private void sendlogs(HttpServletRequest request, ContentCachingResponseWrapper response, long startTime,
                          ContentCachingRequestWrapper reqWrapper) throws IOException {
        RequestScope requestScope = BeanUtils.getBean(RequestScope.class);
        String usrc = "anonymous";
        if (request.getHeader("x-access-token") != null) {
            usrc = "internal";
        }
        String uid = "";
        if (requestScope.getUsername() != null) {
            uid = requestScope.getSub();
            usrc = requestScope.getUsrc();
        }
        Map<String, Object> logs = new HashMap<>();
        String url = getRequestUrl(request);
        logs.put("lt", "dbal");
        logs.put("url", url);
        logs.put("urp", request.getRequestURI());
        logs.put("urq", request.getQueryString() == null ? "" : request.getQueryString());
        logs.put("rt", (System.currentTimeMillis() - startTime) / 1000d);
        logs.put("st", response.getStatus());
        logs.put("mt", request.getMethod());
        logs.put("rmip", request.getHeader("x-real-ip"));
        logs.put("cip", request.getHeader("client-ip"));
        logs.put("cl", request.getContentLength());
        logs.put("rf", request.getHeader("referer") == null ? "" : request.getHeader("referer"));
        logs.put("ua", request.getHeader("user-agent"));
        logs.put("host", request.getHeader("host"));
        logs.put("sn", InetAddress.getLocalHost().getHostName());
        logs.put("tl", DateTimeUtils.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        logs.put("rid", UUID.randomUUID().toString());
        logs.put("uid", uid);
        logs.put("usrc", usrc);
//        logs.put("rqb", new String(reqWrapper.getContentAsByteArray(), Charset.defaultCharset()));
//        logs.put("rpb", IOUtils.toString(response.getContentInputStream(), Charset.defaultCharset()));
        logs.put("bbs", getByteCount(response));
        System.out.println(objectMapper.writeValueAsString(logs));
    }

    private Long getByteCount(ContentCachingResponseWrapper cacheResponse) throws IOException {
        Long totalByte = (long) cacheResponse.getContentSize();
        cacheResponse.copyBodyToResponse();
        return totalByte;
    }

    private String getRequestUrl(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURI());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
