package com.bingchunmoli.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

@WebFilter("/**")
public class CacheFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request == null || request.getContentType() == null) {
            return;
        }
        //如果请求使用jsonBody方式并且没有包装过可重复读取,则包装可重复读取ServletRequest
        if (request.getContentType().contains("json") && !(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper((HttpServletRequest) request);
        }
        super.doFilter(request, response, chain);
    }
}
