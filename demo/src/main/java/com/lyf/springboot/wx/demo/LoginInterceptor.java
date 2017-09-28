package com.lyf.springboot.wx.demo;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

  private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

  private static Set<String> jumpUrls = new HashSet<>();
  static {
    jumpUrls.add("/article/list");
    jumpUrls.add("/article/list-data");
//    jumpUrls.add("")
  }


  public LoginInterceptor() {
  }


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
          throws Exception {
    HttpSession session = request.getSession();
    if(session.getAttribute(SessionConstants.SESSION_AGENT_WXUSER) != null){
      response.setHeader("Cache-Control","no-store");
      response.setHeader("Pragma", "no-cache");
      response.setDateHeader("Expires", 0);
      return true;
    }

    return checkWxBrowser(request, response);
  }

  private boolean checkWxBrowser(HttpServletRequest request, HttpServletResponse response) throws IOException {
    StringBuffer requestUrl = request.getRequestURL();
    String queryParam = request.getQueryString();
    String refUrl = requestUrl.toString();
    if (StringUtils.isNotBlank(queryParam)) {
      refUrl += "?" + queryParam;
    }
    if(jumpUrls.contains(request.getRequestURI())){
      if(isWxBrowser(request)){
        request.getSession().setAttribute(SessionConstants.AUTH_REDIRECT, refUrl);
        response.sendRedirect(String.format("/agent_wx_auth/%s?url=%s", webType(),
                URLEncoder.encode(refUrl, "UTF8")));
        return false;
      } else {
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        return true;
      }
    } else {
      request.getSession().setAttribute(SessionConstants.AUTH_REDIRECT, refUrl);
      response.sendRedirect(String.format("/agent_wx_auth/%s?url=%s", webType(),
              URLEncoder.encode(refUrl, "UTF8")));
      return false;
    }
  }
  private static boolean isWxBrowser(HttpServletRequest request){
    String userAgent = request.getHeader("user-agent");
    return userAgent.contains("MicroMessenger");
  }

  protected int webType (){
    return 1;
  }
  @Override
  public void postHandle(
					HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
          throws Exception {
  }

  @Override
  public void afterCompletion(
					HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
          throws Exception {
  }

}
