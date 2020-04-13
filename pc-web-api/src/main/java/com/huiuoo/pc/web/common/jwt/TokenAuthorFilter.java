package com.huiuoo.pc.web.common.jwt;

import com.alibaba.fastjson.JSON;
import com.huiuoo.pc.common.error.EmBusinessError;
import com.huiuoo.pc.common.response.CommonReturnType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @描述：验证token
 * @创建人：LHF
 * @创建时间：2020/3/18 10:32
 * @param
 * @return
 */
@Slf4j
@Component
@WebFilter(urlPatterns = {"/*"},filterName = "tokenAuthorFilter")
public class TokenAuthorFilter  implements Filter{

    private static Map<String, Object> result;
    static {
        result = new ConcurrentHashMap<>();
    }
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList(
                    "/admin/getToken",
                    "/login/index"
            )));
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init filter config.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;

        rep.setHeader("Access-Control-Allow-Origin","*");
        rep.setCharacterEncoding("UTF-8");
        rep.setContentType("application/json; charset=utf-8");

        log.info("call:"+req.getMethod());
        if ("OPTIONS".equals(req.getMethod())){
            rep.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            rep.setHeader("Access-Control-Max-Age", "3110400000");
            rep.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, PB-TOKEN,Authorization");
            rep.setHeader("Content-Type","multipart/form-data");
            this.write(request,response, CommonReturnType.create(null));
            return;
        }

        String token = req.getHeader("PB-TOKEN");
        log.info("PB-TOKEN:"+token);
        String contextPath = req.getContextPath();
        //不需要过滤的url
        String[] urls = {".js",".css",".ico",".jpg",".png"};
        boolean flag = true;
        for (String str : urls) {
            if (contextPath.contains(str)) {
                flag =false;
                break;
            }
        }
        if (!flag) {
            chain.doFilter(request, response);
        }
        String path = req.getRequestURI().substring(contextPath.length()).replaceAll("[/]+$]","");
        log.info("path:"+path);
        boolean allowedPath = ALLOWED_PATHS.contains(path);
        if (allowedPath){
            log.info("allowed paths pass :"+path);
            chain.doFilter(request,response);
            return;
        }
        boolean isFilter = true;
        if (null == token || token.isEmpty()){
            result.put("errCode", EmBusinessError.STATUS_IS_ERROR.getErrCode());
            result.put("errMsg", EmBusinessError.STATUS_IS_ERROR.getErrMsg());
            this.write(request,response,CommonReturnType.create(result, "fail"));
            isFilter = false;
        }

        boolean token_valid = JwtToken.verifyToken(token);
        if (!token_valid){
            result.put("errCode", EmBusinessError.STATUS_TOKEN_TIMEOUT.getErrCode());
            result.put("errMsg", EmBusinessError.STATUS_TOKEN_TIMEOUT.getErrMsg());
            this.write(request,response,CommonReturnType.create(result, "fail"));
            return;
        }

        if (!isFilter){
            result.put("errCode", EmBusinessError.STATUS_TOKEN_INVALID.getErrCode());
            result.put("errMsg", EmBusinessError.STATUS_TOKEN_INVALID.getErrMsg());
            this.write(request,response,CommonReturnType.create(result, "fail"));
            return;
        }

        long admin_id = JwtToken.getUserId(token);
        if (admin_id < 0){
            result.put("errCode", EmBusinessError.STATUS_TOKEN_INVALID.getErrCode());
            result.put("errMsg", EmBusinessError.STATUS_TOKEN_INVALID.getErrMsg());
            this.write(request,response,CommonReturnType.create(result, "fail"));
            return;
        }
        // 存储当前请求用户id
        request.setAttribute(AdminRequest.CURRENT_USER_ID,admin_id);

        log.info("jwt filter is pass! "+contextPath);
        chain.doFilter(request,response);
    }

    private void write(ServletRequest request, ServletResponse response,CommonReturnType result){
        PrintWriter writer = null;
        OutputStreamWriter osw = null;
        try{
            osw = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
            writer = new PrintWriter(osw,true);
            String jsonStr = JSON.toJSONString(result);
            writer.write(jsonStr);
            writer.flush();
            writer.close();
            osw.close();
        }catch (UnsupportedEncodingException e){
            log.error("过滤器输出数据失败:"+e.getMessage(),e);
        }catch (IOException eo){
            log.error("过滤器输出数据失败IO:"+ eo.getMessage(),eo);
        }finally {
            if (null != writer){
                writer.close();
            }
            if (null != osw){
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
