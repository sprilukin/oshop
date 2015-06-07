package oshop.web.api.rest.adapter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class ReturningRestCallbackAdapter<T> extends GenericRestCallbackAdapter<T> {

    private HttpHeaders headers = new HttpHeaders();

    @Override
    protected ResponseEntity<T> getResponse() throws Exception {
        T result = getResult();
        T convertedResult = convertResult(result);
        return new ResponseBuilder<T>().headers(headers).body(convertedResult).status(HttpStatus.OK).build();
    }

    protected T convertResult(T result) throws Exception {
        return result;
    }

    protected HttpHeaders getHeaders() {
        return headers;
    }

    protected void addHeader(String name, String value) {
        this.headers.set(name, value);
    }

    protected abstract T getResult() throws Exception;
}
