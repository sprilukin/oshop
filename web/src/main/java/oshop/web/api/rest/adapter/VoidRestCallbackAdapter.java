package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class VoidRestCallbackAdapter extends GenericRestCallbackAdapter {
    @Override
    protected ResponseEntity<String> getResponse() throws Exception {
        perform();
        return new ResponseBuilder<String>().status(HttpStatus.NO_CONTENT).build();
    }

    protected abstract void perform() throws Exception;
}
