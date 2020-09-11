package com.lyf.springboot.wx.demo;

import com.lyf.springboot.wx.auto.WeChatProperties;
import com.lyf.springboot.wx.data.WeChatUserInfo;
import com.lyf.springboot.wx.utils.WxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * 此demo用于处理微信公众号授权
 * Created by 永锋 on 2017/9/28.
 */
@Controller
@RequestMapping("/wx")
public class WxAuthController {
    private static final String AUTH_REDIRECT = "redirectUrl";
    private static final Logger logger = LoggerFactory.getLogger(WxAuthController.class);

    @Autowired
    private DemoService demoService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * @param webType 3页面点击授权
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/{webType}")
    public String redirect(@PathVariable("webType") int webType) throws UnsupportedEncodingException {
        if (webType == 3) {
            String refer = request.getHeader("Referer");
            logger.info("refer:{}", refer);
            request.getSession().setAttribute(SessionConstants.AUTH_REDIRECT, refer);
        }
        return "redirect:" + buildRedirectUrl();
    }

    /**
     * 使用即答服务号授权
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    private String buildRedirectUrl() throws UnsupportedEncodingException {
        //TODO 此处应根据实际的域名修改
        String redirectUrl = "http://localhost/" + request.getServerName() + "/wx/auth";
        String redirect_url = WxConstants.loginUrl(weChatProperties.getAppId(), redirectUrl);
        logger.info("url: {}", redirect_url);
        return redirect_url;
    }

    @RequestMapping("/auth")
    public String auth(String code, String state, Model model) {
        String refer = request.getHeader("Referer");
        try {
            logger.info("wx auth state : {}", state);

            WeChatUserInfo wxUser = demoService.authAndUser(code);
            if (wxUser == null) {
                model.addAttribute("errMsg", "微信授权失败，请返回重试");
                model.addAttribute("tryUrl", "http://v.51ruhang.cn");
                return "error";
            }
            HttpSession session = request.getSession();
            String redirectUrl = (String) session.getAttribute(AUTH_REDIRECT);
            if (redirectUrl != null) {
                session.removeAttribute(AUTH_REDIRECT);
                return "redirect:" + redirectUrl;
            } else {
                return "redirect:" + refer;
            }
        } catch (Exception exception) {
            return "redirect:" + refer;
        }
    }

}
