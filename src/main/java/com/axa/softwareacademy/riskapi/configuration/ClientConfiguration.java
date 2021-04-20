package com.axa.softwareacademy.riskapi.configuration;

        import feign.Request;
        import feign.codec.Encoder;
        import org.springframework.beans.factory.ObjectFactory;
        import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
        import org.springframework.cloud.openfeign.support.SpringEncoder;
        import org.springframework.context.annotation.Bean;
        import org.springframework.http.converter.HttpMessageConverter;
        import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class ClientConfiguration {

    private static final int CONNECT_TIMEOUT_MILLIS = 5000;
    private static final int READ_TIMEOUT_MILLIS = 5000;

    @Bean
    public ClientErrorDecoder recaptchaApiClientErrorDecoder() {
        return new ClientErrorDecoder();
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(CONNECT_TIMEOUT_MILLIS, READ_TIMEOUT_MILLIS);
    }


    public Encoder feignEncoder() {
        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new SpringEncoder(objectFactory);
    }

}
