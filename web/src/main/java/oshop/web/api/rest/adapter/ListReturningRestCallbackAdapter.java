package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class ListReturningRestCallbackAdapter<T> extends ReturningRestCallbackAdapter<List<T>> {

    public static final String COLLECTION_SIZE_HEADER = "totalListSize";

    @Override
    protected ResponseEntity<List<T>> getResponse() throws Exception {
        List<T> result = getResult();
        Long size = getSize();

        if (result == null || result.isEmpty()) {
            return new ResponseBuilder<List<T>>().headers(getHeaders()).status(HttpStatus.NO_CONTENT).build();
        } else {
            addHeader(COLLECTION_SIZE_HEADER, size.toString());
            return new ResponseBuilder<List<T>>().headers(getHeaders()).body(result).status(HttpStatus.OK).build();
        }
    }

    @Override
    protected List<T> getResult() throws Exception {
        return convertList(getList());
    }

    protected List<T> convertList(List<T> list) throws Exception {
        return list;
    }

    protected abstract Long getSize() throws Exception;
    protected abstract List<T> getList() throws Exception;
}
