package hska.iwi.products;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1)
public class ResponseHeaderFilter implements Filter {

    private String pod;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // ...
        pod = System.getenv("HOSTNAME");
        if(pod == null) {
            pod = "non-nada-niente";
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader(
                "Kubernetes-Pod", pod);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // ...
    }
}
