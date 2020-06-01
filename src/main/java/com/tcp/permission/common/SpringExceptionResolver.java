package com.tcp.permission.common;

import com.tcp.permission.exception.ParamException;
import com.tcp.permission.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName SpringExceptionResolver
 * @Description: TODO
 * @Author TCP
 * @Date 2020/4/29 0029
 * @Version V1.0
 **/
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
    private static final String URL_END_WITH_JSON = ".json";
    private static final String URL_END_WITH_PAGE = ".page";
    private static final String DEFAULT_ERROR_MSG = "System Error";
    private static final String VIEW_NAME = "jsonView";
    private static final String EXCEPTION_VIEW_NAME = "exception";

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //获取请求的url
        String requestUrl = httpServletRequest.getRequestURL().toString();
        ModelAndView mv;
        //定义 json 请求都以 .json 结尾，page请求都以 .page 结尾
        if (requestUrl.endsWith(URL_END_WITH_JSON)) {
            //判断异常类型,如果是自定义异常，直接抛出异常，如果是非自定义异常，则直接按默认msg返回
            if (e instanceof PermissionException || e instanceof ParamException) {
                log.error("permissionException,url:{},msg:{}", requestUrl,e.getMessage());
                ResponseData failResponseData = ResponseData.fail(500, e.getMessage());
                mv = new ModelAndView(VIEW_NAME, failResponseData.toMap());
            } else {
                log.error("unknown exception,url:{},msg:{}", requestUrl, e.getMessage());
                ResponseData failResponseData = ResponseData.fail(500, DEFAULT_ERROR_MSG);
                mv = new ModelAndView(VIEW_NAME, failResponseData.toMap());
            }
        } else if (requestUrl.endsWith(URL_END_WITH_PAGE)) {
            log.error("unknown exception,url:{},msg:{}", requestUrl, e.getMessage());
            ResponseData failResponseData = ResponseData.fail(500, DEFAULT_ERROR_MSG);
            mv = new ModelAndView(EXCEPTION_VIEW_NAME, failResponseData.toMap());
        } else {
            log.error("unknown exception,url:{},msg:{}", requestUrl, e.getMessage());
            ResponseData failResponseData = ResponseData.fail(500, DEFAULT_ERROR_MSG);
            mv = new ModelAndView(VIEW_NAME, failResponseData.toMap());
        }
        return mv;
    }
}
