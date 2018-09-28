package com.guye.config.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 编写拦截器
 * 使用 @Component 让 Spring 管理其生命周期：
 * @author DHzhang
 *
 */
@Component  //此处可以在webconfig.java 中配置
public class TimeInterceptor implements HandlerInterceptor {
    
	public TimeInterceptor() {
		System.out.println("TimeInterceptor");
	}
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        System.out.println("========preHandle=========");
        System.out.println(handler.getClass().getName());
        if(handler!=null&&!("org.springframework.web.servlet.resource.ResourceHttpRequestHandler").equals(handler.getClass().getName())){
        	try{
        		System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
                System.out.println(((HandlerMethod)handler).getMethod().getName());
        	}catch (Exception e) {
				e.printStackTrace();
			}
        	
        }        
        
        request.setAttribute("startTime", System.currentTimeMillis());
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

        System.out.println("========postHandle=========");
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("耗时:"+(System.currentTimeMillis() - start));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
            throws Exception {

        System.out.println("========afterCompletion=========");
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("耗时:"+(System.currentTimeMillis() - start));
        
        System.out.println(exception);
    }

}
