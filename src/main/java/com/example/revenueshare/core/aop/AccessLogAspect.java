package com.example.revenueshare.core.aop;

import com.example.revenueshare.core.aop.repository.AccessLogRepository;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AccessLogAspect {


    private final AccessLogRepository accessLogRepository;


    @Pointcut("execution(* com.example..presentation.*Controller.*(..))")
    public void presentationLayer() {
    }

    @Pointcut("execution(* com.example..service.*Impl.*(..))")
    public void serviceLayer() {
    }

    @Pointcut("execution(* com.example..domain.*Repository.*(..))")
    public void persistenceLayer() {
    }


    @Around("presentationLayer()")
    public Object addLogOfRequestAndResponse(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        long start = System.currentTimeMillis();

        Object result = null;
        AccessLog accessLog = null;
        try {
            //insert Request Info
            log.info("[BEFORE] : ...");
            accessLog = this.setRequestInfo(request);
            accessLogRepository.save(accessLog);


            //process
            result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

            //update Response Info
            log.info("[AFTER] : ...");
            this.setResponseInfo(accessLog, result);
            accessLogRepository.save(accessLog);


        } catch (RsException e) {
            //update Error Info
            log.error(String.format("[ERROR] : %s", e.getMessage()));
            accessLog.setResponseFail(String.format("%s: %s", e.getErrCd(), this.printStackTraceToString(e)));
            accessLogRepository.save(accessLog);
            result = new ResponseEntity<ResponseVO>(ResponseVO.errBuilder()
                    .errCd(e.getErrCd())
                    .errMsg(e.getMessage())
                    .build(), e.getErrCd().getStatus());
        } catch (Exception e) {
            //update Error Info
            log.error(String.format("[%s ERROR] : %s", ErrCd.UNKNOWN, e.getMessage()), e);
            accessLog.setResponseFail(String.format("%s: %s", ErrCd.UNKNOWN, this.printStackTraceToString(e)));
            accessLogRepository.save(accessLog);
            result = new ResponseEntity<ResponseVO>(ResponseVO.errBuilder()
                    .errCd(ErrCd.UNKNOWN)
                    .errMsg("???????????? ?????? ?????????. ???????????????????????? ??????????????? ????????????.")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            long end = System.currentTimeMillis();
            log.info("Request: {} {}: {}. request Ajax: {} ({}ms)", request.getMethod(), request.getRequestURL(), paramMapToString(request.getParameterMap()), isAjax(request), end - start);

        }

        return result;
    }


    @SuppressWarnings("deprecation")
    private AccessLog setRequestInfo(HttpServletRequest request) throws JsonProcessingException {
        String sessionId = request.getSession().getId();
        String param = null;
        try {
            param = this.requestBodyToString(request);
            if (StringUtils.isEmpty(param))    //request body??? param ??? ?????????..
                param = this.paramMapToJsonString(request.getParameterMap());
        } catch (Exception e) {
            param = String.format("[?????? parameter ?????? ??????]: %s", e.getMessage());
        }

        return AccessLog.reqBuilder()
                .sessionId(sessionId)
                .ip(this.getClientIpAddr(request))
                .httpMethod(request.getMethod())
                .url(request.getRequestURL().toString())
                .uri(request.getRequestURI())
                .param(param)
                .build();
    }

    private void setResponseInfo(AccessLog accessLog, Object result) throws JsonProcessingException {
        String response = null;
        if (result instanceof String)
            response = (String) result;
        else if (result instanceof List)
            response = result.toString();
        else if (result instanceof Map)
            response = result.toString();
        else
            response = String.valueOf(result);

        accessLog.setResponseCompleted(response);
    }


    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet()
                .stream()
                .map(entry -> String.format("%s : %s",
                        entry.getKey(), Arrays.toString(entry.getValue())))
                .collect(Collectors.joining(", "));
    }

    private String paramMapToJsonString(Map<String, String[]> paramMap) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(paramMap);
    }


    private String requestBodyToString(HttpServletRequest request) {
        StringBuffer body = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        } catch (Exception e) {
            log.info("Error reading JSON string: " + e.toString());
        }
        return body.toString();
    }


    /**
     * <pre>????????? ??????: ?????????????????? IP????????? ????????????.
     * 	-L4???????????? ????????? ?????? ?????? ??????????????? request.getRemoteAdder()??? ????????? ???????????? ????????????.
     * 	-?????? ???????????? header??? ????????????(X-Forwarded-For, WL-Proxy-Client-IP ???) ????????? ????????? ????????? ?????? ??????.
     * 	-?????? ???????????? ??? ????????? ???????????? ?????????????????? ???????????? ???????????? ????????? ?????? ?????? ??????.
     * </pre>
     *
     * @param request
     * @return String ???????????? ??? ????????????
     * @author: ?????????
     */
    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    private boolean isAjax(HttpServletRequest request) {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
            return true;

        return false;

    }


    private String printStackTraceToString(Exception e) {
        try {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));

            return errors.toString();
        } catch (Exception ex) {
            return "printStackTrace ????????? ?????? ????????????. " + ex.getMessage();
        }
    }

}
