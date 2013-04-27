package oshop.web.api.rest.adapter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class ReturningRestCallbackAdapter<T> extends GenericRestCallbackAdapter<T> {

    private HttpHeaders headers = new HttpHeaders();

    @Override
    protected ResponseEntity<T> getResponse() throws Exception {
        return new ResponseBuilder<T>().headers(headers).body(getResult()).status(HttpStatus.OK).build();
    }

    protected void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    protected void addHeader(String name, String value) {
        this.headers.set(name, value);
    }

    protected void setContentType(MediaType contentType) {
        this.headers.setContentType(contentType);
    }

    protected abstract T getResult() throws Exception;
}
