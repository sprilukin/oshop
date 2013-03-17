package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import oshop.web.api.dto.GenericListDto;

public abstract class EmptyResultCheckRestCallbackAdapter<T extends GenericListDto> extends ReturningRestCallbackAdapter<T> {
    @Override
    protected ResponseEntity<T> getResponse() throws Exception {
        T result = getResult();
        if (result == null || result.getList().isEmpty()) {
            return new ResponseBuilder<T>().status(HttpStatus.NO_CONTENT).build();
        } else {
            return new ResponseBuilder<T>().body(result).status(HttpStatus.OK).build();
        }
    }
}
