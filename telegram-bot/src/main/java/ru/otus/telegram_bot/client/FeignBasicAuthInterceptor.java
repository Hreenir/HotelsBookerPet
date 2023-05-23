package ru.otus.telegram_bot.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public class FeignBasicAuthInterceptor implements RequestInterceptor {
    private String headerValue;

    public FeignBasicAuthInterceptor() {
        this("user", "user", ISO_8859_1);
    }

    public FeignBasicAuthInterceptor(String username, String password) {
        this(username, password, ISO_8859_1);
    }

    public FeignBasicAuthInterceptor(String username, String password, Charset charset) {
        this.headerValue = base64Encoder((username + ":" + password).getBytes(charset));
    }

    @SneakyThrows
    private String base64Encoder(byte[] bytes) {
        Base64Encoder encoder = new Base64Encoder();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.encode(bytes, 0, bytes.length, baos);
        byte[] outBuf = baos.toByteArray();
        return new String(outBuf);
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Basic " + headerValue);
        requestTemplate.bodyTemplate();
    }
}
