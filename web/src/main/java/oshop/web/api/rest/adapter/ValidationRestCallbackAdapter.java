package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import oshop.web.dto.ValidationFailedDto;
import oshop.web.api.rest.RestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationRestCallbackAdapter implements RestCallback {

    private BindingResult result;
    private RestCallback callback;

    public ValidationRestCallbackAdapter(BindingResult result, RestCallback callback) {
        this.result = result;
        this.callback = callback;
    }

    public ResponseEntity<?> invoke() {
        if (!result.hasErrors()) {
            return callback.invoke();
        } else {
            ResponseBuilder<ValidationFailedDto> builder = new ResponseBuilder<ValidationFailedDto>();
            builder.status(HttpStatus.BAD_REQUEST);

            Map<String, List<String>> fieldErrorsMap = new HashMap<String, List<String>>();
            Map<String, String> errors = new HashMap<String, String>();

            for (ObjectError error: result.getAllErrors()) {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError)error;

                    List<String> fieldErrorsList = fieldErrorsMap.get(fieldError.getField());
                    if (fieldErrorsList == null) {
                        fieldErrorsList = new ArrayList<String>();
                        fieldErrorsMap.put(fieldError.getField(), fieldErrorsList);
                    }


                    fieldErrorsList.add(fieldError.getDefaultMessage());
                } else {
                    errors.put(error.getObjectName(), error.getDefaultMessage());
                }
            }

            ValidationFailedDto body = new ValidationFailedDto(fieldErrorsMap, errors);
            body.setError("Validation failed");

            return builder.body(body).build();
        }
    }
}
