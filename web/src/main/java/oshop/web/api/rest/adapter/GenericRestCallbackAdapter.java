package oshop.web.api.rest.adapter;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import oshop.dao.exception.NotFoundException;
import oshop.web.api.rest.RestCallback;

public abstract class GenericRestCallbackAdapter<T> implements RestCallback {

    public ResponseEntity<?> invoke() {
        try {
            return getResponse();
        } catch (NotFoundException e) {
            return new ResponseBuilder<String>()
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()).build();
        } catch (ConstraintViolationException e) {
            StringBuilder sb = new StringBuilder(e.getMessage()).append(": ");
            sb.append(e.getSQLException().getMessage());

            return new ResponseBuilder<String>()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(sb.toString()).build();
        } catch (Exception e) {
            return new ResponseBuilder<String>()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage()).build();
        }
    }

    protected abstract ResponseEntity<T> getResponse() throws Exception;
}
