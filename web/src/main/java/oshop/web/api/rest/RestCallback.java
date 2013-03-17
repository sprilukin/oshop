package oshop.web.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import oshop.web.api.dto.ValidationFailedDto;

public interface RestCallback {
    public ResponseEntity<?> invoke();
    public ResponseEntity<ValidationFailedDto> invoke(BindingResult result);
}
