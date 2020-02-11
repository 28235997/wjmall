package com.zb.wjmall.interceptor;

import com.zb.wjmall.annotation.LoginRequire;
import com.zb.wjmall.util.CookieUtil;
import com.zb.wjmall.util.HttpClientUtil;
import com.zb.wjmall.util.JwtUtil;
import com.zb.wjmall.util.PassportUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter{


        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


            HandlerMethod method = (HandlerMethod) handler;
            LoginRequire methodAnnotation = method.getMethodAnnotation(LoginRequire.class);

            //方法不需要验证身份
            if (methodAnnotation == null) {
                return true;
            }


            String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
            String newToken = request.getParameter("newToken");
            String token = "";

            if (StringUtils.isNotBlank(oldToken) && StringUtils.isBlank(newToken)) {
                //用户登录过
                token = oldToken;
            }

            if (StringUtils.isBlank(oldToken) && StringUtils.isNotBlank(newToken)) {
                //用户第一次登陆
                token = newToken;
            }

            if (StringUtils.isNotBlank(oldToken) && StringUtils.isNotBlank(newToken)) {
                //用户登陆已过期
                token = newToken;
            }

            //方法需要验证身份，且请求中没有token
            if (methodAnnotation.ifNeedSuccess() && StringUtils.isBlank(token)) {
                StringBuffer requestURL = request.getRequestURL();
                response.sendRedirect("http://passport.wjmall.com:8085/index?returnURL=" + requestURL);
                return false;
            }

            //方法需要验证身份，且请求中有token
            String success = "";
            if (StringUtils.isNotBlank(token)) {
                //远程访问passport，验证token
                success = HttpClientUtil.doGet("http://passport.wjmall.com:8085/verify?token=" + token + "&salt=" + PassportUtil.getRequestAddr(request));
            }
            if (!success.equals("success") && methodAnnotation.ifNeedSuccess()) {
                response.sendRedirect("http://passport.wjmall.com:8085/index");
                return false;
            }
            if (success.equals("success")) {
                //cookie验证通过，重新刷新cookie的过期时间
                CookieUtil.setCookie(request, response, "oldCookie", token, 60 * 60 * 2, true);

                //将用户信息放入应用请求中
                Map userMap = JwtUtil.decode("superlee1010", token, PassportUtil.getRequestAddr(request));
                request.setAttribute("userId", userMap.get("userId"));
                request.setAttribute("nickName", userMap.get("nickName"));
            }
            if (!success.equals("success") && !methodAnnotation.ifNeedSuccess()) {
                return true;
            }

            return true;

        }

}

