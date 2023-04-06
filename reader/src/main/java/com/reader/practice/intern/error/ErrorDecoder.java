package com.reader.practice.intern.error;

import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ErrorDecoder implements feign.codec.ErrorDecoder {

    private final feign.codec.ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 400 -> new ResponseStatusException(HttpStatus.BAD_REQUEST);
            case 401 -> new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            case 402 -> new ResponseStatusException(HttpStatus.FORBIDDEN);
            case 403 -> new ResponseStatusException(HttpStatus.NOT_FOUND);
            default -> errorDecoder.decode(s, response);
        };
    }
}
