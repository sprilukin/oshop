package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public abstract class EmptyResultCheckRestCallbackAdapter<T extends Collection> extends ReturningRestCallbackAdapter<T> {
    @Override
    protected ResponseEntity<T> getResponse() throws Exception {
        T result = getResult();
        if (result == null || result.isEmpty()) {
            return new ResponseBuilder<T>().status(HttpStatus.NO_CONTENT).build();
        } else {
            return new ResponseBuilder<T>().body(result).status(HttpStatus.OK).build();
        }
    }
}
