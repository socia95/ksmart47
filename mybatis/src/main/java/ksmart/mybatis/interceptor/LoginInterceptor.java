package ksmart.mybatis.interceptor;

import java.util.logging.Handler;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor {
		
	
	
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception{
			
			HttpSession session = request.getSession();
			String sessionId = (String)session.getAttribute("SID");
			
			if(session == null) {
				response.sendRedirect("/member/login");
				return false;
				
			}else{
				String requestUri = request.getRequestURI();
				int sessionLevel = (int) session.getAttribute("SLEVEL");
				
				if(sessionLevel == 2) {
					if(requestUri.indexOf("/member/memberList") > -1 ||
					   requestUri.indexOf("/member/modify") 	> -1 ||
					   requestUri.indexOf("/member/remove") 	> -1 ||
					   requestUri.indexOf("/goods/modify") 		> -1 ||
					   requestUri.indexOf("/goods/remove") 		> -1) {
						response.sendRedirect("/");
						return false;
					}
				}if(sessionLevel == 3) {
					if(requestUri.indexOf("/member/memberList") > -1 ||
						requestUri.indexOf("/member/modify") 	> -1 ||
						requestUri.indexOf("/member/remove") 	> -1 ||
						requestUri.indexOf("/goods/modify") 	> -1 ||
						requestUri.indexOf("/goods/remove") 	> -1) {
						response.sendRedirect("/");
						return false;
							}
			}
			return true;
		}
	}
}

