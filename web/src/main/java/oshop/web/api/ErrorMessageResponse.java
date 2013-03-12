package oshop.web.api;

public class ErrorMessageResponse {
    public String message;

    public ErrorMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
