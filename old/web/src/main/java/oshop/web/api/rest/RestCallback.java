package oshop.web.api.rest;

import org.springframework.http.ResponseEntity;

public interface RestCallback {
    public ResponseEntity<?> invoke();
}
