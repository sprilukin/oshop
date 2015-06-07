package oshop.web.api.rest.adapter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder<T> {

    private HttpStatus status;
    private T body;
    private HttpHeaders headers;

    public ResponseBuilder() {
        headers = new HttpHeaders();
    }

    public ResponseBuilder<T> status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseBuilder<T> header(String headerName, String headerValue) {
        headers.set(headerName, headerValue);
        return this;
    }

    public ResponseBuilder<T> headers(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public ResponseBuilder<T> body(T body) {
        this.body = body;
        return this;
    }

    public ResponseEntity<T> build() {
        if (body != null && headers != null && !headers.isEmpty()) {
            return new ResponseEntity<T>(body, headers, status);
        } else if (body != null) {
            return new ResponseEntity<T>(body, status);
        } else if (headers != null) {
            return new ResponseEntity<T>(headers, status);
        } else {
            return new ResponseEntity<T>(status);
        }
    }
}
