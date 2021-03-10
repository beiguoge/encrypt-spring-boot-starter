package com.saisikeji.encrypt.starter.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saisikeji.encrypt.starter.resp.CommonResult;
import com.saisikeji.encrypt.starter.tag.Encrypt;
import com.saisikeji.encrypt.starter.utils.AesEncryptUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 接口加密
 *
 * @author beiguoge
 * @version 1.0
 * @date 2021/3/10 9:51
 */
@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<CommonResult<Object>> {

    @Value("${spring.encrypt.key}")
    private String encryptKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 判断需要加密的接口，即带有@Encrypt注解
     *
     * @param methodParameter MethodParameter
     * @param converterType converterType
     * @return boolean
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(Encrypt.class);
    }

    /**
     * 对接口返回结果进行加密处理
     *
     * @param result 自定义返回
     * @param methodParameter MethodParameter
     * @param mediaType MediaType
     * @param converterType converterType
     * @param request ServerHttpRequest
     * @param response ServerHttpResponse
     * @return CommonResult
     */
    @Override
    public CommonResult<Object> beforeBodyWrite(CommonResult<Object> result, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> converterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            if (result.getMessage() != null) {
                result.setMessage(AesEncryptUtils.aesEncrypt(result.getMessage(), encryptKey));
            }
            if (result.getData() != null) {
                result.setData(AesEncryptUtils.aesEncrypt(objectMapper.writeValueAsString(result.getData()), encryptKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
