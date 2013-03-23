package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import oshop.web.api.dto.ValidationFailedDto;
import oshop.web.api.rest.RestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationFailedRestCallbackAdapter implements RestCallback {

    public ResponseEntity<?> invoke() {
        throw new IllegalStateException("Method not supported");
    }

    @Override
    public ResponseEntity<ValidationFailedDto> invoke(BindingResult result) {
        ResponseBuilder<ValidationFailedDto> builder = new ResponseBuilder<ValidationFailedDto>();
        builder.status(HttpStatus.BAD_REQUEST);

        Map<String, List<String>> fieldErrorsMap = new HashMap<String, List<String>>();
        for (FieldError error: result.getFieldErrors()) {
            List<String> fieldErrorsList = fieldErrorsMap.get(error.getField());
            if (fieldErrorsList == null) {
                fieldErrorsList = new ArrayList<String>();
                fieldErrorsMap.put(error.getField(), fieldErrorsList);
            }


            fieldErrorsList.add(error.getDefaultMessage());
        }

        Map<String, String> errors = new HashMap<String, String>();
        for (ObjectError error: result.getAllErrors()) {
            if (!(error instanceof FieldError)) {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }

        ValidationFailedDto body = new ValidationFailedDto(fieldErrorsMap, errors);
        body.setError("Validation failed");

        return builder.body(body).build();
    }
}
