package com.guye.config.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 要是该过滤器生效，有两种方式：

1) 使用 @Component 注解

2) 添加到过滤器链中，此方式适用于使用第三方的过滤器。将过滤器写到 WebConfig 类中，如下：
@Bean
public FilterRegistrationBean timeFilter() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    
    TimeFilter timeFilter = new TimeFilter();
    registrationBean.setFilter(timeFilter);
    
    List<String> urls = new ArrayList<>();
    urls.add("/*");
    registrationBean.setUrlPatterns(urls);
    
    return registrationBean;
}
 * @author DHzhang
 *
 */

public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("=======初始化过滤器=========");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        long start = System.currentTimeMillis();

        filterChain.doFilter(request, response);

        System.out.println("filter 耗时：" + (System.currentTimeMillis() - start));

    }

    @Override
    public void destroy() {
        System.out.println("=======销毁过滤器=========");
    }

}