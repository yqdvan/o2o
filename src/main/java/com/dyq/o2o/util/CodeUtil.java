package com.dyq.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil{

    /**
     * 对比校验码
     *
     * @param request
     * @return
     */
    public static boolean checkVerifyCode(HttpServletRequest request) {
        // 获取输入的校验码
        String verifyCodeActual = HttpServletRequestUtil.getString(request,
                "verifyCodeActual");
        // 获取图片中的校验码
        String verifyCodeExpexted = (String) request.getSession().getAttribute(
                Constants.KAPTCHA_SESSION_KEY);
        // 对比
        if (verifyCodeActual == null || !verifyCodeActual.equalsIgnoreCase(verifyCodeExpexted)) {
            return false;
        }
        return true;
    }
}
