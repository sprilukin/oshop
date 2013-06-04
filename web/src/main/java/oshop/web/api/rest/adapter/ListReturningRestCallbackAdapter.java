package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import oshop.dto.PaginatedCollectionList;

import java.util.List;

public abstract class ListReturningRestCallbackAdapter<T> extends ReturningRestCallbackAdapter<List<T>> {

    public static final String COLLECTION_SIZE_HEADER = "totalListSize";

    @Override
    protected ResponseEntity<List<T>> getResponse() throws Exception {
        List<T> result = getResult();

        if (result == null || result.isEmpty()) {
            return new ResponseBuilder<List<T>>().headers(getHeaders()).status(HttpStatus.NO_CONTENT).build();
        } else {
            long size = result.size();

            if (result instanceof PaginatedCollectionList) {
                size = ((PaginatedCollectionList<T>)result).getTotalCollectionSize();
            }

            addHeader(COLLECTION_SIZE_HEADER, String.valueOf(size));
            List<T> list = convertList(result);
            return new ResponseBuilder<List<T>>().headers(getHeaders()).body(list).status(HttpStatus.OK).build();
        }
    }

    @Override
    protected abstract List<T> getResult() throws Exception;

    protected List<T> convertList(List<T> list) throws Exception {
        return list;
    }
}
