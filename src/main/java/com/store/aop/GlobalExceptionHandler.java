package com.store.aop;

import com.store.model.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author licfe
 * 统一捕捉shiro的异常，返回给前台一个json信息，前台根据这个信息显示对应的提示，或者做页面的跳转。
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 不满足@RequiresGuest注解时抛出的异常信息
     */
    private static final String GUEST_ONLY = "Attempting to perform a guest-only operation";

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResultData handleThrowable(Throwable e) {
        log.error("执行出错：" + e.getMessage(), e);
        return ResultData.toResultData(false, "500", "系统异常，请联系管理员。");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultData handleRuntimeException(RuntimeException e) {
        log.error("执行出错：" + e.getMessage(), e);
        return ResultData.toResultData(false, "500", "系统异常，请联系管理员。");
    }

    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public ResultData handleShiroException(ShiroException e) {
        log.error("Shiro执行出错：" + e.getMessage(), e);
        return ResultData.toResultData(false, "500", "鉴权或授权过程出错");
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public ResultData handleUnauthenticatedException(UnauthenticatedException e) {
        log.error("Shiro执行出错：" + e.getMessage(), e);
        String eMsg = e.getMessage();
        if (StringUtils.startsWithIgnoreCase(eMsg,GUEST_ONLY)){
            return ResultData.toResultData(false, "401", "只允许游客访问，若您已登录，请先退出登录");
        }else{
            return ResultData.toResultData(false, "401", "用户未登录");
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResultData handlUnauthorizedException() {
        return ResultData.toResultData(false, "403", "用户没有访问权限");
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseBody
    public ResultData handlIncorrectCredentialsException() {
        return ResultData.toResultData(false, "403", "用户名或密码错误！");
    }

    @ExceptionHandler(DisabledAccountException.class)
    @ResponseBody
    public ResultData handlDisabledAccountException() {
        return ResultData.toResultData(false, "403", "账号已禁用！");
    }

}