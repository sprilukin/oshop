package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import oshop.dao.exception.NotFoundException;
import oshop.web.api.dto.ValidationFailedDto;
import oshop.web.api.rest.RestCallback;

public abstract class GenericRestCallbackAdapter<T> implements RestCallback {

    public ResponseEntity<?> invoke() {
        try {
            return getResponse();
        } catch (NotFoundException e1) {
            return new ResponseBuilder<String>()
                    .status(HttpStatus.NOT_FOUND)
                    .body(convertStringToJSON(e1.getMessage())).build();
        } catch (Exception e) {
            return new ResponseBuilder<String>()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(convertStringToJSON(e.getMessage())).build();
        }
    }

    @Override
    public ResponseEntity<ValidationFailedDto> invoke(BindingResult result) {
        throw new IllegalStateException("Method not supported");
    }

    private String convertStringToJSON(String string) {
        return String.format("\"%s\"", string);
    }

    protected abstract ResponseEntity<T> getResponse() throws Exception;
}
