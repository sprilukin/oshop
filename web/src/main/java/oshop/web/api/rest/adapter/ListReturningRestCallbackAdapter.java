package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import oshop.dto.GenericListDto;

import java.util.List;

public abstract class ListReturningRestCallbackAdapter<T> extends ReturningRestCallbackAdapter<List<T>> {

    public static final String COLLECTION_SIZE_HEADER = "totalListSize";

    @Override
    protected ResponseEntity<List<T>> getResponse() throws Exception {
        GenericListDto<T> result = getListDto();

        if (result == null || result.getList().isEmpty()) {
            return new ResponseBuilder<List<T>>().headers(getHeaders()).status(HttpStatus.NO_CONTENT).build();
        } else {
            addHeader(COLLECTION_SIZE_HEADER, result.getSize().toString());
            List<T> list = convertList((List<T>) result.getList());
            return new ResponseBuilder<List<T>>().headers(getHeaders()).body(list).status(HttpStatus.OK).build();
        }
    }

    @Override
    protected List<T> getResult() throws Exception {
        throw new IllegalStateException("Not supported");
    }

    protected List<T> convertList(List<T> list) throws Exception {
        return list;
    }

    protected abstract GenericListDto<T> getListDto() throws Exception;
}
