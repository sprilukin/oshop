package oshop.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


public class ValidationFailedDto {

    private String error;

    @JsonProperty("fields")
    private Map<String, List<String>> fields;

    @JsonProperty("errors")
    private Map<String, String> errors;

    public ValidationFailedDto(Map<String, List<String>> fields, Map<String, String> errors) {
        this.fields = fields;
        this.errors = errors;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, List<String>> getFields() {
        return fields;
    }

    public void setFields(Map<String, List<String>> fields) {
        this.fields = fields;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
