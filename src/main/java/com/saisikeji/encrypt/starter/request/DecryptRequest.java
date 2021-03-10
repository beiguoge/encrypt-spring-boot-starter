package com.saisikeji.encrypt.starter.request;

import com.saisikeji.encrypt.starter.tag.Decrypt;
import com.saisikeji.encrypt.starter.utils.AesEncryptUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * 接口解密
 *
 * @author beiguoge
 * @version 1.0
 * @date 2021/3/10 10:11
 */
@ControllerAdvice
public class DecryptRequest extends RequestBodyAdviceAdapter {

    @Value("${spring.encrypt.key}")
    private String encryptKey;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(Decrypt.class) || methodParameter.hasParameterAnnotation(Decrypt.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            byte[] body = new byte[inputMessage.getBody().available()];
            inputMessage.getBody().read(body);
            String decrypt = AesEncryptUtils.aesDecrypt(Base64.encodeBase64String(body), encryptKey);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decrypt.getBytes(StandardCharsets.UTF_8));
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() {
                    return byteArrayInputStream;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }

}
