package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import oshop.dao.exception.NotFoundException;
import oshop.web.api.dto.ValidationFailedDto;
import oshop.web.api.rest.RestCallback;

import java.util.HashMap;
import java.util.Map;

public class ValidationFailedRestCallbackAdapter implements RestCallback {

    public ResponseEntity<?> invoke() {
        throw new IllegalStateException("Method not supported");
    }

    @Override
    public ResponseEntity<ValidationFailedDto> invoke(BindingResult result) {
        ResponseBuilder<ValidationFailedDto> builder = new ResponseBuilder<ValidationFailedDto>();
        builder.status(HttpStatus.BAD_REQUEST);

        Map<String, String> fieldErrors = new HashMap<String, String>();
        for (FieldError error: result.getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        ValidationFailedDto body = new ValidationFailedDto(fieldErrors);
        body.setError("Validation failed");

        return builder.body(body).build();
    }
}
