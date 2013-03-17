package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class ReturningRestCallbackAdapter<T> extends GenericRestCallbackAdapter<T> {
    @Override
    protected ResponseEntity<T> getResponse() throws Exception {
        return new ResponseBuilder<T>().body(getResult()).status(HttpStatus.OK).build();
    }

    protected abstract T getResult() throws Exception;
}
