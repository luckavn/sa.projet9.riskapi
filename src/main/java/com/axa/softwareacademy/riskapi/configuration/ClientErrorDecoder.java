package com.axa.softwareacademy.riskapi.configuration;

import feign.Response;
import feign.codec.ErrorDecoder;

public class ClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return new Default().decode(methodKey, response);
    }
}
